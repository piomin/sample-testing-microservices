package pl.piomin.performance.tests

import java.util.concurrent.TimeUnit

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration

class CreateTripPerformanceTest extends Simulation {

  val scn = scenario("CreateTrip").repeat(10, "n") {
    exec(http("AddTrip-API")
      .post("http://localhost:8090/trips")
      .header("Content-Type", "application-json")
      .body(StringBody("""{"destination":"John${n}","locationX":${n},"locationY":${n},"username":"user${n}"}"""))
      .check(status.is(200))
    ).pause(Duration.apply(5, TimeUnit.MILLISECONDS))
  }

  setUp(scn.inject(atOnceUsers(20))).maxDuration(FiniteDuration.apply(5, TimeUnit.MINUTES))
}
