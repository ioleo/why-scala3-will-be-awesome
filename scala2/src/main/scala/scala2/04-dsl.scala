package scala2.dsl

object ExampleA extends App {
  import Html._

  val page = html(
    div(
      h1("Scala 2 DSL"),
      p(
        "Lorem ipsum dolor sit amet, ",
        b("consectetur"),
        " adipiscing elit."
      )
    ),
    div("footer")
  )

  println(page)
}

// -----------------------------------------------------

sealed abstract class Html(val label: String, val contents: Html*) {
  def render(level: Int)(implicit s: StringBuilder): Unit = {
    val ident = "\n" + ("  " * level)
    s.append(s"$ident<$label>")
    contents.foreach(_.render(level + 1))
    s.append(s"$ident</$label>")
  }
}

// -----------------------------------------------------

object Html {

  case class p(override val contents: Html*)   extends Html("p")
  case class b(override val contents: Html*)   extends Html("b")
  case class h1(override val contents: Html*)  extends Html("h1")
  case class div(override val contents: Html*) extends Html("div")
  case class text(value: String)               extends Html("text") {
    override def render(level: Int)(implicit s: StringBuilder): Unit =
      s.append(value)
  }
  
  def html(contents: Html*): String = {
    implicit val s: StringBuilder = new StringBuilder
    s.append("<html>")
    contents.foreach(_.render(1))
    s.append("\n</html>")
    s.toString
  }
  
  implicit def toText(value: String): text = text(value)
}