
object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(38); 
  var v1 = new Var("1");System.out.println("""v1  : Var = """ + $show(v1 ));$skip(29); val res$0 = 
  Inferencer.MakeFreshTVar();System.out.println("""res0: TVar = """ + $show(res$0));$skip(23); val res$1 = 
  
  Lambda.parse("x");System.out.println("""res1: Term = """ + $show(res$1));$skip(29); val res$2 = 
  Lambda.parse("\"string\"");System.out.println("""res2: Term = """ + $show(res$2));$skip(20); val res$3 = 
  Lambda.parse("3");System.out.println("""res3: Term = """ + $show(res$3));$skip(23); val res$4 = 
  Lambda.parse("true");System.out.println("""res4: Term = """ + $show(res$4));$skip(30); val res$5 = 
  Lambda.parse("""\x => x""");System.out.println("""res5: Term = """ + $show(res$5));$skip(30); val res$6 = 
  Lambda.parse("""\x => 1""");System.out.println("""res6: Term = """ + $show(res$6));$skip(34); val res$7 = 
  Lambda.parse("""\x => (x x)""");System.out.println("""res7: Term = """ + $show(res$7));$skip(37); val res$8 = 
  Lambda.parse("""\x => (x + x )""");System.out.println("""res8: Term = """ + $show(res$8));$skip(33); val res$9 = 
  Lambda.parse("let x = x in x");System.out.println("""res9: Term = """ + $show(res$9));$skip(33); val res$10 = 
  Lambda.parse("let x = 3 in x");System.out.println("""res10: Term = """ + $show(res$10));$skip(52); val res$11 = 
  Lambda.parse("let x = 3 in let y = 2 in (x + y)");System.out.println("""res11: Term = """ + $show(res$11));$skip(71); val res$12 = 
  Lambda.parse("""let f = \x => x in let _ = (f true) in (f false)""");System.out.println("""res12: Term = """ + $show(res$12));$skip(97); val res$13 = 
  // infer
  Inferencer Infer Lambda.parse("""let f = \x => x in let _ = (f 3) in (f false)""" );System.out.println("""res13: Type = """ + $show(res$13));$skip(87); val res$14 = 
  Inferencer Infer Lambda.parse("""let f = \x => (1 + x) in let _ = (f 3) in (f 1)""");System.out.println("""res14: Type = """ + $show(res$14));$skip(87); val res$15 = 
  Inferencer Infer Lambda.parse("""let f = \x => (x + 1) in let _ = (f 3) in (f 1)""");System.out.println("""res15: Type = """ + $show(res$15));$skip(98); val res$16 = 
  
  Inferencer Infer Lambda.parse("""let f = (\x => (\x => x)) in let _ = (f 3) in (f "str")""");System.out.println("""res16: Type = """ + $show(res$16))}
                                     
}
