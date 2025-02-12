package munPackage
import scala.collection.mutable.Buffer
import scala.io.StdIn.readLine
import scala.util.Try
import java.time.LocalDate
import scala.language.postfixOps


object Demo1 {
  def main(args: Array[String]): Unit = {

    tallentaminen.lue()     // Old data is brought
    val persons = tallentaminen.persons

    // 5 cars and 10 skisets
    var allItems =
      Buffer[Item](
      Car(1, 60, true, None, None),  Car(2, 80, true, None, None),  Car(3, 100, true, None, None),  Car(4, 50, false, None, None),  Car(5, 70, false, None, None),
      Ski(6, 50, "S", None, None),  Ski(7, 50, "S", None, None),  Ski(8, 70, "S", None, None),
      Ski(9, 50, "M", None, None),  Ski(10, 50, "M", None, None),  Ski(11, 50, "M", None, None),  Ski(12, 70, "M", None, None),
      Ski(13, 50, "L", None, None),  Ski(14, 50, "L", None, None),  Ski(15, 70, "L", None, None)
      )



    println("Welcome to G-Rental! To use the program we need your name and ID.")
    println("First, write your name:")
    val name = readLine()
    println("Then, your ID (e.g. '210603H069J')")
    val id = readLine()

    if !persons.contains(Person(name, id, _)) then    // If a new customer then makes a new person and adds it to "persons"
      val uusPersoona = new Person(name, id, Buffer[Item]())
      persons += uusPersoona
    val nytPersoona = persons.find( kyseinenJaba =>     // Now both cases can be found from "persons" by name and ID
      name == kyseinenJaba.kokonimii && id == kyseinenJaba.identification).get
    println(s"Hello $name!")



    // Variables of the menu options
    var onOff = true        // keeps the program running
    var renting = false     // renting option
    var returning = false   // returning option
    var state = false       // state option



    while onOff do

      // The options menu
      println("\nIf you are interested in:")
      println(" 1. renting an item, write 'rent'")
      println(" 2. returning an item, write 'return'")
      println(" 3. checking the state of each item, write 'state'")
      println(" 4. exiting the program, write 'exit'")

      // Setting up the options through the variables
      readLine().toLowerCase match
        case "rent" => renting = true
        case "return" => returning = true
        case "state" => state = true
        case "exit" =>
          renting = false
          onOff = false
        case _ =>



      val availableItems = allItems.filterNot(
          item => persons.exists( _.rentedItems.exists(_.tuotenumeero == item.tuotenumeero) )
        )
      val notAvailabeItems = allItems.filterNot(item => availableItems.contains(item))



      //RENTING//
      while renting do
        println("Are you interested in a car or ski equipment? (write 'car' or 'ski')")
        val tyyppi = readLine().toLowerCase
        var pvHinta = 0

        tyyppi.head match
          case 'c' =>
            println("Here are the options. Pick a car by writing its product number.")
            availableItems.filter(_.tuotenumeero < 6)
              .foreach(_.printInfo())
          case 's' =>
            println("Here are the options. Pick a skiset by writing its product number.")
            availableItems.filter(_.tuotenumeero >= 6)
              .foreach(_.printInfo())
          case _ =>

        val tuoteNr = readLine().toInt
        val haluttuTuote = allItems.find(tuoteNr == _.tuotenumeero).get

        // If the product is rented for more than a week, the price-per-day is cheaper and for more than e month more cheaper.
        println("How many days would you like the product?")
        val days = readLine().toInt

        // Computing the price by days
        val vuokraamisenHinta =
          if days < 8 then         days * haluttuTuote.hintaa
          else if days < 30 then   days * haluttuTuote.hintaa * 0.84
          else if days >= 30 then  days * haluttuTuote.hintaa * 0.65

        println(s"That will cost a total of $vuokraamisenHinta €")
        println("To make this purchase write 'r'. To go back to the options write anything else.")
        val confirmationOrNot = readLine().toLowerCase
        if confirmationOrNot=="r" then
          haluttuTuote.alkuPvMuuttuja = Some(LocalDate.now())                       // Setting up new renting dates
          haluttuTuote.loppuPvMuuttuja = Some(LocalDate.now().plusDays(days))
          nytPersoona.vuokraamassaATM += haluttuTuote
          renting = false                                                  // Shutting down the renting to go back to the menu
          tallentaminen.kirjoita()                                         // The changes are stored
        else renting = false



      //RETURNING//
      while returning do
        if nytPersoona.vuokraamassaATM.nonEmpty then
          println("Here are your products you're renting:")
          nytPersoona.vuokraamassaATM.foreach(_.printState())

          println("Write the product number of the product you're returning. To go back to the start write any letter.")
          val mikaNr = readLine()
          if mikaNr.toIntOption.isDefined then
            val poistettavaItem = nytPersoona.rentedItems
              .find(mikaNr.toInt == _.tuotenumeero).get
            poistettavaItem.alkuPvMuuttuja = None                                  // Resetting the renting dates to None
            poistettavaItem.loppuPvMuuttuja = None
            nytPersoona.vuokraamassaATM -= poistettavaItem
            returning = false                                             // Shutting down the returing to go back to the menu
            tallentaminen.kirjoita()                                      // The changes are stored
          else returning = false
        else println("You have no items rented at the moment.")
          returning = false


      //STATE//
      while state do
        println("\nAvailable items: ")
        var availabilityStatus = false
        allItems.foreach { item =>
          val productNumberExists = persons.exists(
            _.rentedItems.exists( _.tuotenumeero == item.tuotenumeero ))    // Rented by someone if it has the same product number
          if !productNumberExists then                                      // If there is a product which isn't rented by some person..
            item.printState()                                               // ..print its state
            availabilityStatus = true
        }
        if !availabilityStatus then println("-")
        availabilityStatus = false

        println("\nNot available items: ")
        if persons.forall(_.rentedItems.isEmpty) then println("-")     // If all "persons" don't have items then there are only available items
        else
          persons.foreach(
            _.rentedItems.foreach(_.printState())
          )
        println("\nPress enter to go back")
        readLine()
        state = false

  }
}