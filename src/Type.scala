abstract class Type

case class Prim(name: String) extends Type {
  override def toString = name
}

// Lambda type: t1 -> t2
case class Arrow(t1: Type, t2: Type) extends Type {
  override def toString = t1.toString() + "->" + (t2 match {case Arrow(_, _) => "("+  t2.toString() + ")" case _ =>  t2.toString()})
}

// Type variable, name is the variable name
case class TVar(name: String) extends Type {
  var realType: Type = this;
  override def toString = if (realType == this) name else realType.toString()
}

