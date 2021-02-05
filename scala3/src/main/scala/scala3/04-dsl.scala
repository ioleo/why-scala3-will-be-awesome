package scala3.dsl

@main
def exampleA =
  import DefinitionOne.Html.{given, _}

  val page = html(
    div(
      h1("Scala 3 DSL"),
      p(
        "Lorem ipsum dolor sit amet, ",
        b("consectetur"),
        " adipiscing elit."
      )
    ),
    div("footer")
  )

  println(page)

// ---------------------------------------------------

object DefinitionOne:

  opaque type Html = (StringBuilder, Int) ?=> Unit

  object Html:

    private def element(label: String, contents: Seq[Html]): Html =
      (s: StringBuilder, level: Int) ?=>
        val ident = "\n" + ("  " * level)
        s.append(s"$ident<$label>")
        given nextLevel: Int = level + 1
        contents.foreach(_.apply)
        s.append(s"$ident</$label>")

    def p(contents: Html*): Html   = element("p", contents)
    def b(contents: Html*): Html   = element("b", contents)
    def h1(contents: Html*): Html  = element("h1", contents)
    def div(contents: Html*): Html = element("div", contents)
    def text(value: String): Html  = (s: StringBuilder, _: Int) ?=> s.append(value)

    def html(contents: Html*): String =
      given s: StringBuilder = new StringBuilder
      given Int = 1
      s.append("<html>")
      contents.foreach(_.apply)
      s.append("\n</html>")
      s.toString

    // ---------------------------------------------------
    
    given Conversion[String, Html] = new Conversion[String, Html]:
      def apply(value: String): Html = text(value)

// ---------------------------------------------------
    
object note_nice_to_have:
  import DefinitionOne.Html
  // type Element = (Html*) => Html

  // private val element: String => Element =
  //   (label: String) => (contents: Html*) => (s: StringBuilder, level: Int) ?=>
  //     val ident = "\n" + ("  " * level)
  //     s.append(s"$ident<$label>")
  //     given nextLevel: Int = level + 1
  //     contents.foreach(_.apply)
  //     s.append(s"$ident</$label>")

  // val p: Element   = element("p")
  // val b: Element   = element("b")
  // val h1: Element  = element("h1")
  // val div: Element = element("div")

// ---------------------------------------------------

object DefinitionTwo:

  opaque type Html = (StringBuilder, Int) ?=> Unit

  object Html:

    opaque type P <: Html    = Html
    opaque type B <: Html    = Html
    opaque type H1 <: Html   = Html
    opaque type Div <: Html  = Html
    opaque type Text <: Html = Html

    private def element(label: String, contents: Seq[Html]): Html =
      (s: StringBuilder, level: Int) ?=>
        val ident = "\n" + ("  " * level)
        s.append(s"$ident<$label>")
        given nextLevel: Int = level + 1
        contents.foreach(_.apply)
        s.append(s"$ident</$label>")

    // ---------------------------------------------------

    def p(contents: (Text | B)*): P = element("p", contents)
    def b(contents: Text*): B       = element("b", contents)
    def h1(contents: Text*): H1     = element("h1", contents)
    def div(contents: Html*): Div   = element("div", contents)
    def text(value: String): Text   = (s: StringBuilder, _: Int) ?=> s.append(value)

    // ---------------------------------------------------

    def html(contents: Html*): String =
      given s: StringBuilder = new StringBuilder
      given level: Int = 1
      s.append("<html>")
      contents.foreach(_.apply)
      s.append("\n</html>")
      s.toString

    // ---------------------------------------------------
    
    given Conversion[String, Text] = new Conversion[String, Text]:
      def apply(value: String): Text = text(value)

// ---------------------------------------------------
    
@main
def enhancedExampleA =
  import DefinitionTwo.Html.{given, _}

  val page = html(
    div(
      h1("Scala 3 DSL"),
      p(
        "Lorem ipsum dolor sit amet, ",
        b("consectetur"),
        " adipiscing elit."
      )
    ),
    div("footer")
  )

  println(page)