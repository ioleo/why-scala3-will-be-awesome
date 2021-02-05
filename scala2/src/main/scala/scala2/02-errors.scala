package scala2.errors

sealed trait DomainError
class NonNumericInput() extends DomainError
class DivisionByZero()  extends DomainError
class NegativeInput()   extends DomainError

object Errors {

  def parse(input: String): Either[NonNumericInput, Int] =
    input
      .toIntOption
      .toRight(new NonNumericInput())

  def divideBy(input: Int): Either[DivisionByZero, Double] =
    Either.cond(input != 0, 100D / input, new DivisionByZero())

  def assertNonNegative(input: Int): Either[NegativeInput, Unit] =
    Either.cond(input < 0, (), new NegativeInput())

  // ---------------------------------------------------------------

  def run(input: String): Unit = {
    val output =
      for {
        value  <- parse(input)
        // _      <- assertNonNegative(value)
        result <- divideBy(value)
      } yield result

    output.fold(
      _ match {
        case _: NonNumericInput => println(s"$input -> Provided non-numeric input")
        case _: DivisionByZero  => println(s"$input -> Attempted division by zero")
        case _ => ???
      }, 
      v => println(s"$input -> it divides 100 exacly $v times")
    )
  }
}

object ExampleA extends App {
  import Errors._

  run("2")
  run("foo")
  run("0")
  run("-5")
}