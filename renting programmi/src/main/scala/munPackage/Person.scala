package munPackage
import munPackage.*
import scala.collection.mutable.Buffer

class Person(fullname: String, ID: String, val rentedItems: Buffer[Item]):

  val kokonimii = fullname
  val identification = ID
  val vuokraamassaATM = rentedItems
  
  def jokubuffer = rentedItems.map(_.jokuobject)
  def jokuperson: PersonCase = PersonCase(fullname, ID, jokubuffer)
  
