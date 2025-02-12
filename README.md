# scala-renting-program

Rental Management System

 
Introduction
This project is a rental management system that keeps track of customer rentals, specifically cars and ski sets. The system records which items have been rented and their rental periods. The goal is to provide a user-friendly and efficient way to manage the rental process.


Key Features
* Simple rental process – users can easily view available items and rent them.
* Rental management – users can check their rented items and return them.
* State overview – displays all available and rented items, including rental dates.
* JSON-based storage – user data and rental details are saved and loaded automatically.

How to Use
The program starts by running the main function. Upon launch, it prompts the user for their name and ID number and creates a Person object.

The main menu provides four options:
* Rent – lists available items (cars and ski sets separately) along with their prices. The user can rent an item.
* Return – shows the user's rented items and their rental dates. The user can return items.
* State – lists all items, both available and rented, along with rental periods.
* Exit – terminates the program.

  
Technical Implementation
The program is implemented in Scala 3 and utilizes the following structures and libraries:
* Data structures: mutable.Buffer, Map, immutable.List
* Storage: Circe JSON processing library
* Main components:
 - Person – user object
 - Item – rentable products
 - demo1 – contains the main function
 - Tallentaminen – handles data storage and retrieval

   
Data Storage and Retrieval
The program saves user and rental data in JSON format.
* write() – saves data in JSON format.
* read() – loads JSON data back into the program.

  
Pricing Model
Rental prices are calculated as follows:
* Less than 7 days – standard daily rate.
* 7+ days – daily rate reduced to 84% of the original price.
* 30+ days – daily rate reduced to 65% of the original price.

Testing
The program has been manually tested via the command line. The tests included:
* Verifying correct item availability in the state menu.
* Ensuring that an item cannot be rented twice.
* Checking how the system handles users with the same name but different IDs.

References & Resources
* [Aalto University Plus Studio](https://plus.cs.aalto.fi/studio_2/k2023/)
* https://circe.github.io/circe/
* https://www.baeldung.com/
