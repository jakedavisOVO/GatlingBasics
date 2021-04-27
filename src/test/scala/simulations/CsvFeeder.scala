package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends Simulation{
  
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val csvFeeder = csv("Data/game.csv").circular

  def getSpecificVideoGame() = {
    repeat(100) {
      feed(csvFeeder)
        .exec(http("Get specific game")
        .get("videogames/${gameId}")
        .check(jsonPath("$.name").is("${gameName}"))
          .check(status.is(200)))
    }
  }

  val scn = scenario("CSV Feeder")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols((httpConf))
}
