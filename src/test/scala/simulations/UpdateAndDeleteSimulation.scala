package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class UpdateAndDeleteSimulation extends Simulation {
//  Http config
  val httpConfig = http.baseUrl("https://reqres.in/")
    .header("Content-type", value = "application/json")

//  scenario
  val scn = scenario("Update and delete a user")
    .exec(http("Update a user")
    .put("/api/users/2")
    .body(RawFileBody("./src/test/resources/bodies/UpdateUser.json")).asJson
    .check(status.in(200 to 210)))

    .exec(http("Delete a user")
    .delete("/api/users/2")
    .check(status.in(200 to 204)))

//  setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)

}
