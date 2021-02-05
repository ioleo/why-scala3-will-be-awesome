package scala3.extending_lib

import javax.measure.Quantity
import javax.measure.quantity.Length
import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units.METRE

@main
def exampleA =
  import extension_methods.{km, m, cm}
  import extension_methods.{minus, + => pliusas }

  val distance = 1.km minus 500.m pliusas 20.cm
  println(s"[extension methods] It is $distance")

// ------------------------------------------------

object extension_methods:
  import scala.annotation.targetName

  extension (value: Double)
    @targetName("milimeters")
    def mm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(1000)).nn

    @targetName("centrimeters")
    def cm: Quantity[Length] = Quantities.getQuantity(value, METRE.divide(100)).nn

    @targetName("meters")
    def m: Quantity[Length]  = Quantities.getQuantity(value, METRE).nn

    @targetName("kilometers")
    def km: Quantity[Length] = Quantities.getQuantity(value, METRE.multiply(1000)).nn

  extension (self: Quantity[Length])
    infix def +(that: Quantity[Length]): Quantity[Length] = self.add(that).nn
    infix def -(that: Quantity[Length]): Quantity[Length] = self.subtract(that).nn

    infix def plus(that: Quantity[Length]): Quantity[Length]  = self.add(that).nn
    infix def minus(that: Quantity[Length]): Quantity[Length] = self.subtract(that).nn