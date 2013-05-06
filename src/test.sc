
object test {
  var v1 = new Var("1")                           //> v1  : Var = 1
  Inferencer.MakeFreshTVar()                      //> res0: TVar = t1
  
  Lambda.parse("x")                               //> res1: Term = x
  Lambda.parse("\"string\"")                      //> res2: Term = string: String
  Lambda.parse("3")                               //> res3: Term = 3.0: Double
  Lambda.parse("true")                            //> res4: Term = true: Bool
  Lambda.parse("""\x => x""")                     //> res5: Term = (\x => x)
  Lambda.parse("""\x => 1""")                     //> res6: Term = (\x => 1.0: Double)
  Lambda.parse("""\x => (x x)""")                 //> res7: Term = (\x => (x, x))
  Lambda.parse("""\x => (x + x )""")              //> res8: Term = (\x => ((+, x), x))
  Lambda.parse("let x = x in x")                  //> res9: Term = Let x = x in
                                                  //|     x)
  Lambda.parse("let x = 3 in x")                  //> res10: Term = Let x = 3.0: Double in
                                                  //|     x)
  Lambda.parse("let x = 3 in let y = 2 in (x + y)")
                                                  //> res11: Term = Let x = 3.0: Double in
                                                  //|     Let y = 2.0: Double in
                                                  //|         ((+, x), y)))
  Lambda.parse("""let f = \x => x in let _ = (f true) in (f false)""")
                                                  //> res12: Term = Let f = (\x => x) in
                                                  //|     Let _ = (f, true: Bool) in
                                                  //|         (f, false: Bool)))
  // infer
  Inferencer Infer Lambda.parse("""let f = \x => x in let _ = (f 3) in (f false)""" )
                                                  //> res13: Type = Bool
  Inferencer Infer Lambda.parse("""let f = \x => (1 + x) in let _ = (f 3) in (f 1)""")
                                                  //> res14: Type = Double
  Inferencer Infer Lambda.parse("""let f = \x => (x + 1) in let _ = (f 3) in (f 1)""")
                                                  //> res15: Type = Double
  
  Inferencer Infer Lambda.parse("""let f = (\x => (\x => x)) in let _ = (f 3) in (f "str")""")
                                                  //> res16: Type = String->String
                                     
}