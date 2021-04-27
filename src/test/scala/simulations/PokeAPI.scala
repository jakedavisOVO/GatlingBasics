package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class PokeAPI extends Simulation {
  val httpConf = http.baseUrl("https://pokeapi.co/api/v2/")
    .header("Accept", "application/json")



      def getPokemonVoltorb() = {
        repeat(1) {
          exec(http("Get Voltorb")
            .get("pokemon/100")
            .check(jsonPath("$.name").is("voltorb"))
            .check(status.is(200)))
            .pause(1)
        }
      }




  val scn = scenario("Get Pokemons")
    .exec(getPokemonVoltorb())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols((httpConf))
}
