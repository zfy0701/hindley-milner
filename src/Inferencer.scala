import scala.collection.mutable.{ Map, HashMap, Set }

object Inferencer {
  type Context = Map[Var, Type]

  private var count = 0
  def MakeFreshTVar(): TVar = {
    count = count + 1
    new TVar("t" + count)
  }

  def Find(t: Type): Type = {
    t match {
      case tv: TVar => {
        if (tv.pointTo != tv) tv.pointTo = Find(tv.pointTo)
        tv.pointTo
      }
      case _ => t
    }
  }

  def Union(ta: TVar, tb: Type) = {
    // currently the system dosn't support recursive type, so need check
    def occurs(ta: TVar, tb: Type): Boolean = {
      Find(tb) match {
        case _: TVar => ta == tb // `ta`
        case Arrow(t1, t2) => occurs(ta, t1) || occurs(ta, t2)
        case _ => false
      }
    }

    if (ta != tb) {
      if (occurs(ta, tb))
        throw new Exception("no rec type please")
      // do not need find again here
      ta.pointTo = tb
    }
  }

  def Unify(ta: Type, tb: Type): Unit = {
    val (t1, t2) = (Find(ta), Find(tb))
    (t1, t2) match {
      case (Arrow(a1, b1), Arrow(a2, b2)) => { Unify(a1, a2); Unify(b1, b2) }
      case (tv: TVar, _) => Union(tv, tb)
      case (_, tv: TVar) => Union(tv, ta)
      case _ => if (t1 != t2) throw new Exception("unify error, type mismatch")
    }
  }

  def Inst(t: Type, ctx: Context): Type = {
    val bindVar = ctx.values.toSet
    val gen = new HashMap[TVar, TVar]
    def reify(t: Type): Type = { // Try to iterate all free TVar of the type
      Find(t) match {
        case tv: TVar => {
          // If it is a polytype (generic type), that is, not bind by the context create a new Type variable to be subst free.
          if (!bindVar.contains(tv)) gen.getOrElseUpdate(tv, MakeFreshTVar())
          else tv
        }
        case Arrow(t1, t2) => Arrow(reify(t1), reify(t2))
        case _ => t
      }
    }
    reify(t)
  }

  // given type annotation, synthesize the type info and recursively infer the type
  //def Synthesize(ctx: Context, expr: Term, t: Type)

  // type inference, the first element is the type of the expression while context has all other
  // type information
  def Analyze(ctx: Context, expr: Term): Type = {
    // TODO: if there is type annotation, apply synthesize rule
    val res: Type = expr match {
      case Const(v, t) => t
      case v: Var => {
        ctx.get(v) match {
          case Some(t) => Inst(t, ctx)
          case None => throw new Exception("current syntax doesn't need this")
        }
      }
      case Abs(x, e) => {
        val tx = ctx.get(x) match { case Some(t) => t case None => MakeFreshTVar() }
        val te = Analyze(ctx + ( x -> tx ), e)
        Arrow(tx, te)
      }
      case Let(x, e1, e2) => {
        var te1 = Analyze(ctx, e1)
        Analyze(ctx + { x -> te1 }, e2)
      }
      case App(e1, e2) => {
        val te1 = Analyze(ctx, e1)
        val te2 = Analyze(ctx, e2)
        val t = MakeFreshTVar()
        Unify(te1, Arrow(te2, t))
        t
      }
    }
    expr.t = Some(res)
    res
  }

  def Infer(expr: Term): Type = {
    val ctx = new HashMap[Var, Type] +
      { Var("+") -> Arrow(TVar("t0"), Arrow(TVar("t0"), TVar("t0"))) }
    Analyze(ctx, expr)
  }

  def main(args: Array[String]) {
    val expr = Lambda.parse("""let f = (\x => (\x => x)) in let _ = (f 3) in (f "str")""")
    println(expr)
    Inferencer.Infer(expr)
    println(expr)
  }
}