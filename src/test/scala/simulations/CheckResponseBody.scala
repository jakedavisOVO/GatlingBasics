package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBody extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val scn = scenario("Check JSON Path")

    .exec(http("Get Specific Game")
      .get("videogames/1")
      .check(jsonPath("$.name").is("Resident Evil 4")))
      .pause(5)

    .exec(http("Get game ID")
    .get("videogames")
    .check(jsonPath("$[1].id").saveAs("gameId")))

    .exec(http("Get specific video game from game id")
    .get("videogames/${gameId}")
    .check(jsonPath("$.name").is("Gran Turismo 3"))
      .check(jsonPath("$.id").is("2"))
    .check(bodyString.saveAs("responseBody")))
    .exec { session => println(session("responseBody").as[String]); session}

    setUp(
      scn.inject(atOnceUsers(1))
      ).protocols((httpConf))

}
