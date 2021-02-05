package scala3.typeclass

object Data:
  case class Person(name: String, surname: String, age: Int)
  case class Car(brand: String, model: String, productionYear: Int)
  case class TaxiDriver(car: Car, driver: Person, licenseNumber: Int)
    
// ---------------------------------------------    

@main
def usage =
  import Data._
  import Definition._
  import Plugin.{given Show[TaxiDriver]}

  val person     = Person("Mary", "Sue", 33)
  val car        = Car("Skoda", "Superb", 2019)
  val taxiDriver = TaxiDriver(car, person, 712)

  // def printAll[A: Show](items: A*) =
  def printAll[A](items: A*)(using Show[A]) =
    items.foreach(i => println(i.show))

  printAll(person)
  printAll(car)
  printAll(taxiDriver)
    
// ---------------------------------------------    

object Definition:

  trait Show[A]:
    extension (a: A) def show: String

  object Show:
    import Data._

    // pre-packaged show instances
    given Show[Person] = new Show[Person]:
      extension (p: Person) def show: String = s"${p.name} ${p.surname}"

    given Show[Car] = c => s"${c.brand} ${c.model}"

// ---------------------------------------------    

object Plugin_full:
  import Data._
  import Definition._

  // this is the full syntax for a derrived instance
  // it's easy to remember, as it mimicks the method definition syntax
  given showTaxiDriverA(using s1: Show[Person], s2: Show[Car]): Show[TaxiDriver] =
    t => s"${t.driver.show} #${t.licenseNumber} driving ${t.car.show} in Scala 3!"

  def showTaxiDriverB(implicit s1: Show[Person], s2: Show[Car]): Show[TaxiDriver] =
    t => s"${t.driver.show} #${t.licenseNumber} driving ${t.car.show} in Scala 3!"

// ---------------------------------------------    

object Plugin:
  import Data._
  import Definition._

  // we don't really need to name these instances
  // all we care about is that they are present in scope
  // which enables the `show` extension method
  // so we can skip the labels
  given (using Show[Person], Show[Car]): Show[TaxiDriver] =
    t => s"${t.driver.show} #${t.licenseNumber} driving ${t.car.show} in Scala 3!"
