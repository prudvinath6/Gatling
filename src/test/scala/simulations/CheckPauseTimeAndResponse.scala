package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class CheckPauseTimeAndResponse extends Simulation{
// Http config
  val httpConfig = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")

//  scenario
  val scn = scenario("Get all users")
    .exec(http("Get all users")
      .get("/api/users?page=2")
      .check(status.is(200)))
      .pause(3)

    // status in between 200 to 210 and pause randomly selected in between 1 to 10.
    .exec(http("Get single user")
      .get("/api/users/2")
      .check(status.in(200 to 210))
    ).pause(1,10)

    // status not 400 or 500 and pause in milliseconds.
    .exec(http("Single user not found API")
      .get("/api/users/23")
      .check(status.not(400), status.not(500))
    ).pause(3000.milliseconds)

//  setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)
}
