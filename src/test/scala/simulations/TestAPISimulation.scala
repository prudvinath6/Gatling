package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class TestAPISimulation extends Simulation{
  // http configuration
  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json")

  // create a scenario
  val scn = scenario("get User")
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200))

  // create a setup method
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}