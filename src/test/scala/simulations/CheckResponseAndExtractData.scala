package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseAndExtractData extends Simulation {
//  Http config
  val httpConfig = http.baseUrl("https://gorest.co.in")
    .header("Authorization", value = "Bearer f37ba8bd79371ff60ad8109c26fad79b405e7581b9fc1f98a7267cd099ce71aa")

//  scenario
  val scn = scenario("Get a user")
    .exec(http("Get all users")
      .get("/public/v1/users")
      .check(jsonPath("$.data[0].id").saveAs("userID"))
    )

    .exec(http("Get user details")
      .get("/public/v1/users/${userID}")
      .check(status.is(200))
      .check(jsonPath("$.data.id").is("3597"))
      .check(jsonPath("$.data.name").is("dsadaddasd"))
      .check(jsonPath("$.data.email").is("dsadasda@fsdf.gmail"))
    )

//  setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)
}