package simulations

import io.gatling.core.Predef._
import io.gatling.http .Predef._
import scala.concurrent.duration.DurationInt

class CodeReuseWithObject extends Simulation {
  val httpConf = http.baseUrl("http://localhost:8080/app/")
  .header("Accept", "application/json")

    def getAllVideoGames() = {
        repeat(3) {
          exec(http("Get all video games - 1st call")
            .get("videogames")
            .check(status.is(200)))
        }
    }

    def getSpecificVideoGame() = {
      exec(http("Get specific game")
        .get("videogames/1")
        .check(status.is(200)))
    }

     val scn = scenario("Video Game DB checks")
        .exec(getAllVideoGames())
       .pause(5)
       .exec(getSpecificVideoGame())
       .pause(3)
       .exec(getAllVideoGames())
       .pause(1)

    setUp(
      scn.inject(atOnceUsers(1))
    ).protocols((httpConf))

}