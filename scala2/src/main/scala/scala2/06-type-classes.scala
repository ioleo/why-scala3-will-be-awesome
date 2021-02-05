package scala2.typeclass

object Data {
  case class Person(name: String, surname: String, age: Int)
  case class Car(brand: String, model: String, productionYear: Int)
  case class TaxiDriver(car: Car, driver: Person, licenseNumber: Int)
}

object Usage extends App {
  import Data._
  import Definition._
  import Plugin.givenShowTaxiDriver

  val person     = Person("John", "Doe", 42)
  val car        = Car("Toyota", "Avensis", 2011)
  val taxiDriver = TaxiDriver(car, person, 613)

  // def printAll[A: Show](items: A*) =
  def printAll[A](items: A*)(implicit s: Show[A]) =
    items.foreach(i => println(i.show))

  printAll(person)
  printAll(car)
  printAll(taxiDriver)
}

object Definition {
  trait Show[A] {
    def show(a: A): String
  }

  implicit class ShowSyntax[A: Show](a: A) {
    def show: String = implicitly[Show[A]].show(a)
  }

  object Show {
    import Data.{Person, Car}

    // pre-packaged show instances
    implicit val givenShowPerson: Show[Person] = new Show[Person] {
      def show(p: Person): String = s"${p.name} ${p.surname}"
    }

    implicit val givenShowCar: Show[Car] = c => s"${c.brand} ${c.model}"
  }
}

object Plugin {
  import Data._
  import Definition._

  implicit def givenShowTaxiDriver(implicit p: Show[Person], c: Show[Car]): Show[TaxiDriver] =
    t => s"${t.driver.show} #${t.licenseNumber} driving ${t.car.show} in Scala 2!"
}