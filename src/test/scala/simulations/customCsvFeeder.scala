package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class customCsvFeeder extends Simulation {


  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val idNumbers = (1 to 10).iterator
  val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next))

  def getSpecificVideoGame() = {
    repeat(10){
      feed(customFeeder)
        .exec(http("Get specific game")
          .get("videogames/${gameId}")
          .check(status.is(200)))
    }
  }

  val scn = scenario("CSV Feeder")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols((httpConf))

}
