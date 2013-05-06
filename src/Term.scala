
import scala.collection.immutable.StringOps

sealed abstract class Term {
  // This is the type annotation in the language
  var t: Option[Type] = None

  def typeInfo = {
    t match {
      case None => ""
      case Some(ty) => ": " + ty.toString()
    }
  }

  def toString(n: Int): String
  override def toString = toString(0)
}

case class Const(v: Any, ty: Type) extends Term {
  t = Some(ty)
  override def toString(n: Int) = (" " * n) + v.toString + typeInfo
}

case class Var(name: String) extends Term {
  override def toString(n: Int) = (" " * n) + name.toString + typeInfo
}

case class Abs(x: Var, e: Term) extends Term {
  override def toString(n: Int) = (" " * n) + "(\\" + x.toString(0) + " => " + e.toString(0) + ")" + typeInfo
}

case class App(e1: Term, e2: Term) extends Term {
  override def toString(n: Int) = (" " * n) + "(" + e1.toString(0) + ", " + e2.toString(0) + ")" + typeInfo
}

case class Let(x: Var, e1: Term, e2: Term) extends Term {
  override def toString(n: Int) = (" " * n) + "Let " + x.toString(0) + " = " + e1.toString(0) + " in\n" + e2.toString(n + 4) + ")" + typeInfo
}