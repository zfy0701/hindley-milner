import scala.util.parsing.combinator._
import util.parsing.combinator.syntactical._

object Lambda extends StandardTokenParsers with ImplicitConversions {
  lexical.delimiters ++= ("\\ => + - * / ( ) , == = ;" split ' ')
  lexical.reserved ++= ("let in true false" split ' ')

  def boolean: Parser[Term] = ("true" | "false") ^^ { s => Const(s.toBoolean, Prim("Bool")) }
  def string: Parser[Term] = stringLit ^^ { s => Const(s, Prim("String")) }
  def double: Parser[Term] = numericLit ^^ { s => Const(s.toDouble, Prim("Double")) }

  def literal: Parser[Term] = boolean | string | double

  def variable: Parser[Var] = ident ^^ Var

  def let: Parser[Term] = ("let" ~> variable) ~ ("=" ~> expr) ~ ("in" ~> expr) ^^ Let

  def lam: Parser[Term] = ("\\" ~> variable) ~ ("=>" ~> expr) ^^ Abs

  def app: Parser[Term] = "(" ~> expr ~ expr <~ ")" ^^ App

  def operator: Parser[Term] = ("+" | "-" | "*" | ident) ^^ Var
  def op: Parser[Term] = "(" ~> expr ~ operator ~ expr <~ ")" ^^ { case e1 ~ o ~ e2 => App(App(o, e1), e2) }

  def expr: Parser[Term] = variable | literal | lam | let | app | op | "(" ~> expr <~ ")"

  def parse(input: String): Term =
    phrase(expr)(new lexical.Scanner(input)) match {
      case Success(e, _) => e
      case e: NoSuccess => throw new Exception(e.msg)
    }
}
