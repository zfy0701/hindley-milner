// Add an JUnit test

object test {
  List(2,3).apply(0)                              //> res0: Int = 2
  var v1 = new Var("1")                           //> v1  : Var = 1
  Inferencer.MakeFreshTVar()                      //> res1: TVar = t1
  
  Lambda.parse("x")                               //> res2: Term = x
  Lambda.parse("\"string\"")                      //> res3: Term = string: String
  Lambda.parse("3")                               //> res4: Term = 3.0: Double
  Lambda.parse("true")                            //> res5: Term = true: Bool
  Lambda.parse("""\x => x""")                     //> res6: Term = (\x => x)
  Lambda.parse("""\x => 1""")                     //> res7: Term = (\x => 1.0: Double)
  Lambda.parse("""\x => (x x)""")                 //> res8: Term = (\x => (x, x))
  Lambda.parse("""\x => (x + x )""")              //> res9: Term = (\x => ((+, x), x))
  Lambda.parse("let x = x in x")                  //> res10: Term = Let x = x in
                                                  //|     x)
  Lambda.parse("let x = 3 in x")                  //> res11: Term = Let x = 3.0: Double in
                                                  //|     x)
  Lambda.parse("let x = 3 in let y = 2 in (x + y)")
                                                  //> res12: Term = Let x = 3.0: Double in
                                                  //|     Let y = 2.0: Double in
                                                  //|         ((+, x), y)))
  Lambda.parse("""let f = \x => x in let _ = (f true) in (f false)""")
                                                  //> res13: Term = Let f = (\x => x) in
                                                  //|     Let _ = (f, true: Bool) in
                                                  //|         (f, false: Bool)))
  // infer
  Inferencer Infer Lambda.parse("""let f = \x => x in let _ = (f 3) in (f false)""" )
                                                  //> res14: Type = Bool
  Inferencer Infer Lambda.parse("""let f = \x => (1 + x) in let _ = (f 3) in (f 1)""")
                                                  //> res15: Type = Double
  Inferencer Infer Lambda.parse("""let f = \x => (x + 1) in let _ = (f 3) in (f 1)""")
                                                  //> res16: Type = Double
  
  Inferencer Infer Lambda.parse("""let f = (\x => (\x => x)) in let _ = (f 3) in (f "str")""")
                                     
}