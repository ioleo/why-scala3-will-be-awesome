package scala2.running

object EntrypointA {

  def main(args: Array[String]): Unit =
    println("Go Scala 2! [A] " + args.mkString(", "))
}

// -----------------------------------------------------

object EntrypointB extends App {

  println("Go Scala 2! [B] " + args.mkString(", "))
}

// -----------------------------------------------------

object EntrypointC extends App {
  EntrypointA.main(Array("foo"))
  EntrypointB.main(Array("bar", "baz"))
  println("Go Scala 2! [C]")
}