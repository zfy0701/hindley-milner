
abstract case class Expr

case class Var(name : String) extends Expr {

}

class Abs(x : Var, e : Expr) extends Expr {
 
}

class App(e1 : Expr, e2 : Expr) extends Expr {
  
}


