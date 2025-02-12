package munPackage
import scala.collection.mutable.Buffer
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.Json
import io.circe.syntax.*
import io.circe.parser.decode
import io.circe.Codec
import io.circe.Decoder.importedDecoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.semiauto.deriveCodec

import java.io.PrintWriter
import scala.io.Source
import java.io.*
import java.time.LocalDate
import scala.util.Try

case class Persons(listi: Buffer[PersonCase])
case class PersonCase(fullname: String, ID: String, rentedItems: Buffer[CarSkiCase])
case class CarSkiCase(productNumber: Int, price: Int, alkupv: Option[LocalDate], loppupv: Option[LocalDate], skiBox: Boolean, size: String, isCar: Boolean)

implicit val carskicasDecoder: Codec[CarSkiCase] = deriveCodec[CarSkiCase]
implicit val personcaseDecoder: Codec[PersonCase] = deriveCodec[PersonCase]
implicit val personssicaseDecoder: Codec[Persons] = deriveCodec[Persons]

object tallentaminen {

  var persons = Buffer[Person]()

  def jokupersonkeis(personII: Buffer[Person]) =
    val res = Persons(persons.map(_.jokuperson))
    //println(res.asJson)
    res


  def kirjoita() =
    val filealuks = new File("jSOn/jokuT")
    val filewriter = new PrintWriter(filealuks)
    filewriter.write(jokupersonkeis(persons).asJson.spaces2)
    filewriter.close()

  def lue() =
    var tallennettuStringi = ""
    try
      // opens an incoming character stream from the file
      val fileIn = FileReader("jSOn/jokuT")

      // Creates a buffered stream with which it is possible to
      // read line by line from the previous stream
      val linesIn = BufferedReader(fileIn)

      // At this point, the streams should be open
      // so we must remember to close them.
      try
        // Read the text from the stream line by line until the read line
        // is null. Then we know that
        // the stream (and also the file) end has been reached.
        var oneLine = linesIn.readLine()
        while oneLine != null do
          tallennettuStringi += oneLine + "\n"
          oneLine = linesIn.readLine()

      finally
        // Close open streams
        // This will be executed if the file has been opened
        // regardless of whether or not there were any exceptions.
        fileIn.close()
        linesIn.close()
      end try
    catch
      case notFound: FileNotFoundException => println("croski ei toi löydy")
      // Response here to a failed file opening.
      case e: IOException => println("croski ei toi toimi")
      // Response here to unsuccessful reading

    
    val people =
      for
        jokane <- parse(tallennettuStringi)
        person <- jokane.as[Persons]
      yield
        person

    people match
      case Left(error: Error) => println("ei kay")
      case Right(value) =>
        for perss <- value.listi do
          val ihmine = new Person(perss.fullname, perss.ID, Buffer())
          for itemii <- perss.rentedItems do
            if itemii.isCar then
              ihmine.rentedItems += new Car(itemii.productNumber, itemii.price, itemii.skiBox, itemii.alkupv, itemii.loppupv)
            else ihmine.rentedItems += new Ski(itemii.productNumber, itemii.price, itemii.size, itemii.alkupv, itemii.loppupv)
          end for
          persons += ihmine







}
