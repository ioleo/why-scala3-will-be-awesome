package scala2.extending_lib

import javax.measure.Quantity
import javax.measure.quantity.Length
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units.METRE

object ExampleA extends App {
  import ImplicitConversions._

  val distance = 1.km - 500.m + 20.cm
  println(s"[implicit conversions] It is $distance")
}

object ImplicitConversions {

  class DoubleOps(value: Double) {
    // short names
    def mm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(1000))
    def cm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(100))
    def m: Quantity[Length]  = Quantities.getQuantity(value, METRE)
    def km: Quantity[Length] = Quantities.getQuantity(value, METRE.multiply(1000))

    // long aliases
    def milimeters: Quantity[Length]  = mm
    def centimeters: Quantity[Length] = cm
    def meters: Quantity[Length]      = m
    def kilometers: Quantity[Length]  = km
  }

  implicit def toDoubleOps(value: Double): DoubleOps = new DoubleOps(value)

  class LengthOps(self: Quantity[Length]) {
    def +(that: Quantity[Length]): Quantity[Length] = self.add(that)
    def -(that: Quantity[Length]): Quantity[Length] = self.subtract(that)

    def plus(that: Quantity[Length]): Quantity[Length] = self.add(that)
    def minus(that: Quantity[Length]): Quantity[Length] = self.subtract(that)
  }

  implicit def toLengthOps(value: Quantity[Length]): LengthOps = new LengthOps(value)
}

object ExampleB extends App {
  import ImplicitClass._

  val distance = 1.km - 500.m + 20.cm
  println(s"[implicit class] It is $distance")
}

object ImplicitClass {

  implicit class DoubleOps(value: Double) {
    // short names
    def mm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(1000))
    def cm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(100))
    def m: Quantity[Length]  = Quantities.getQuantity(value, METRE)
    def km: Quantity[Length] = Quantities.getQuantity(value, METRE.multiply(1000))

    // long aliases
    def milimeters: Quantity[Length]  = mm
    def centimeters: Quantity[Length] = cm
    def meters: Quantity[Length]      = m
    def kilometers: Quantity[Length]  = km
  }

  implicit class LengthOps(self: Quantity[Length]) {
    def +(that: Quantity[Length]): Quantity[Length] = self.add(that)
    def -(that: Quantity[Length]): Quantity[Length] = self.subtract(that)

    def plus(that: Quantity[Length]): Quantity[Length] = self.add(that)
    def minus(that: Quantity[Length]): Quantity[Length] = self.subtract(that)
  }
}


