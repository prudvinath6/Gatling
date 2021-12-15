package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoopRequest extends Simulation{
// Http Config
  val httpConfig = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header("Content-type", value = "application/json")

//  Reusable functions
  def getAllUsersRequest() = {
    repeat(2){
      exec(http("Get all users")
        .get("/api/users")
        .check(status.is(200))
      )
    }
  }

  def getUserRequest() = {
    repeat(2){
      exec(http("Get single user")
      .get("/api/users/2")
      .check(status.is(200))
      )
    }
  }

  def addUser() = {
    repeat(2){
      exec(http("Add a user")
        .post("/api/users")
        .body(RawFileBody("./src/test/resources/bodies/AddUser.json")).asJson
        .check(status.in(200 to 201))
      )
    }
  }

// Scenario
  val scn = scenario("Repeat requests")
    .exec(getAllUsersRequest())
    .pause(2)
    .exec(getUserRequest())
    .pause(2)
    .exec(addUser())

// Setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)
}
