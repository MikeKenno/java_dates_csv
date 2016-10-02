## Quick java example
create a csv listing the salary dates and bonus dates
* For the salary if the date is a weekend day move the date back to the Friday
* For the bonus move the date to the following Wednesday

## Running the example
It was built against java 8 and uses maven to manage the dependencies

* clone the project and cd into the directory

* To build the app use maven and run 
`mvn package`

* To run the app and produce the csv run 
`java -jar target/tdd-example-1.0-SNAPSHOT.jar`

## Todo

Initially the app works and has some tests but some of the changes I'd like to make are:
* Break the csv writing code out of the main app file
* Add command line argument parsing






