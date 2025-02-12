package munPackage
import java.time.LocalDate


abstract class Item(productNumber: Int, price: Int, var alku: Option[(LocalDate)], var loppu: Option[LocalDate]):

  var alkuPvMuuttuja = alku
  var loppuPvMuuttuja = loppu
  val tuotenumeero = productNumber
  val hintaa = price

  def printInfo(): Unit
  def printState(): Unit
  def jokuobject: CarSkiCase


class Car(productNumber: Int, price: Int, skiBox: Boolean, alku: Option[LocalDate], loppu: Option[LocalDate]) extends Item(productNumber, price, alku, loppu):

  def printInfo(): Unit =
    if skiBox then println(s"Product number $productNumber: $price €, skibox")
    else println(s"Product number $productNumber: $price €")

  def printState(): Unit =
    if skiBox then
      alkuPvMuuttuja match
        case Some(moi) => println(s"Car, Product number $productNumber: $price €, skibox, reservation: ${alkuPvMuuttuja.get} till ${loppuPvMuuttuja.get}")
        case None => println(s"Car, Product number $productNumber: $price €, skibox, no reservation")
    else
      alkuPvMuuttuja match
        case Some(moi) => println(s"Car, Product number $productNumber: $price €, reservation: ${alkuPvMuuttuja.get} till ${loppuPvMuuttuja.get}")
        case None => println(s"Car, Product number $productNumber: $price €, no reservation")

  override def jokuobject: CarSkiCase = CarSkiCase(productNumber, price, alkuPvMuuttuja, loppuPvMuuttuja, skiBox, "", true)


class Ski(productNumber: Int, price: Int, size: String, alku: Option[LocalDate], loppu: Option[LocalDate]) extends Item(productNumber, price, alku, loppu):

  def printInfo(): Unit = println(s"Product number $productNumber: $price €, size: $size")

  def printState(): Unit =
    alkuPvMuuttuja match
      case Some(moi) => println(s"Ski, Product number $productNumber: $price €, size: $size, reservation: ${alkuPvMuuttuja.get} till ${loppuPvMuuttuja.get}")
      case None => println(s"Ski, Product number $productNumber: $price €, size: $size, no reservation")

  override def jokuobject: CarSkiCase = CarSkiCase(productNumber, price, alkuPvMuuttuja, loppuPvMuuttuja, false, size, false)