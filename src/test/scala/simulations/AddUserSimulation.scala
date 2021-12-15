package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class AddUserSimulation extends Simulation{
// http configuration
  val httpConfig = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json")

//create a scenario
  val scn = scenario("Create user")
    .exec(http("Create a User")
      .post("/api/users")
      .body(RawFileBody("./src/test/resources/bodies/AddUser.json")).asJson
      .check(status is 201))

//create a setup method
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)
}