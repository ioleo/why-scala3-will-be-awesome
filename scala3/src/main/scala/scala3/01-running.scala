package scala3.running

@main
def entrypointA(args: String*): Unit =
  println("Go Scala 3! [A] >> " + args.mkString(", "))

// -----------------------------------------------------

@main
def entrypointB: Unit =
  println("Go Scala 3! [B]")

// -----------------------------------------------------

@main
def entrypointC(bool: Boolean, int: Int, args: String*): Unit =
  println(s"Go Scala 3! [C] $bool, $int >> " + args.mkString(", "))
  // sbt "scala3/runMain scala3.running.entrypointB false 7 foo"

// -----------------------------------------------------
  
@main
def entrypointD: Unit =
  entrypointA("foo")
  entrypointB
  entrypointC(true, 42, "bar", "baz")
  println("Go Scala 3! [D]")

// -----------------------------------------------------

object custom_example:
  enum FooBarBaz:
    case Foo, Bar, Baz

  import scala.util.CommandLineParser.FromString

  given FromString[FooBarBaz] with
    def fromString(s: String) = FooBarBaz.valueOf(s)

  @main
  def entrypointE(fooBarBaz: FooBarBaz): Unit =
    println(s"Go Scala 3! [E] $fooBarBaz")
    // sbt "scala3/runMain scala3.running.entrypointE Baz"