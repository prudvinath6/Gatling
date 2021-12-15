package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class DataFeederCSV extends Simulation{
// HttpConfig
  val httpConfig = http.baseUrl("https://gorest.co.in")
    .header("Authorization", value = "Bearer f37ba8bd79371ff60ad8109c26fad79b405e7581b9fc1f98a7267cd099ce71aa")

//  Feeder
  val csvFeeder = csv("./src/test/resources/data/getUser.csv").circular

  def getUser() = {
    repeat(7){
      feed(csvFeeder)
        .exec(http("get single user")
          .get("/public/v1/users/${userId}")
          .check(status.in(200,304))
          .check(jsonPath("$.data.name").is("${user}"))
        )
        .pause(2)
    }
  }

// Scenario
  val scn = scenario("get Users")
    .exec(getUser())

// Setup
//  setUp(scn.inject(atOnceUsers(1))).protocols(httpConfig)
  setUp(
    scn.inject(
      nothingFor(5),
//      constantUsersPerSec(10) during(10 seconds),
      atOnceUsers(10),
      rampUsersPerSec(1) to (5) during(20 seconds)
    ).protocols(httpConfig)
  )
}
