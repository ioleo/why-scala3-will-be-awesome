package scala3.errors

sealed trait DomainError
class NonNumericInput() extends DomainError
class DivisionByZero()  extends DomainError
class NegativeInput()   extends DomainError

object Errors:
  import custom_datatype.{given, _}

  def parse(input: String): Or[NonNumericInput, Int] =
    input
      .toIntOption
      .toRHS(NonNumericInput())

  def divideBy(input: Int): Or[DivisionByZero, Double] =
    Or.cond(input != 0, 100D / input, DivisionByZero())

  def assertNonNegative(input: Int): Or[NegativeInput, Unit] =
    Or.cond(input < 0, (), NegativeInput())

  // ---------------------------------------------------------------

  def run(input: String): Unit =
    val output =
      for
        value  <- parse(input)
        // _      <- assertNonNegative(value)
        result <- divideBy(value)
      yield result
      
    output.fold(
      _ match
        case _: NonNumericInput => println(s"$input -> Provided non-numeric input")
        case _: DivisionByZero  => println(s"$input -> Attempted division by zero")
      , 
      v => println(s"$input -> it divides 100 exacly $v times")
    )

// -----------------------------------------------------

@main 
def exampleA =
  import Errors._
  run("4")
  run("bar")
  run("0")

// ---------------------------------------------------------------

object custom_datatype:
  sealed trait Or[+E, +A]:
    self =>
      def flatMap[E1, B](f: A => Or[E1, B]): Or[E | E1, B] =
        self match
          case RHS(a)      => f(a)
          case lhs: LHS[E] => lhs

      def map[B](f: A => B): Or[E, B] =
        self match
          case RHS(a)      => RHS(f(a))
          case lhs: LHS[E] => lhs

      def fold[C](err: E => C, suc: A => C): C =
        self match
          case RHS(a) => suc(a)
          case LHS(e) => err(e)
        
  case class LHS[+E](lhs: E) extends Or[E, Nothing]
  case class RHS[+A](rhs: A) extends Or[Nothing, A]

  object Or:
    def cond[E, A](test: Boolean, right: => A, left: => E): Or[E, A] =
      if (test) RHS(right) else LHS(left)

  extension [E, A](opt: Option[A])
    def toRHS(default: => E): Or[E, A] =
      opt match
        case Some(a) => RHS(a)
        case None    => LHS(default)