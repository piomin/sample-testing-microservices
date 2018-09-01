package pl.piomin.performance.tests

import java.util.concurrent.TimeUnit

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.FiniteDuration
import scala.util.Random

class CreateAndPayTripPerformanceTest extends Simulation {

  val rPassengers = Iterator.continually(Map("passenger" -> List("walker","smith","hamilton","scott","holmes").lift(Random.nextInt(5)).get))

  val scn = scenario("CreateAndPayTrip").feed(rPassengers).repeat(100, "n") {
    exec(http("CreateTrip-API")
      .post("http://localhost:8090/trips")
      .header("Content-Type", "application/json")
      .body(StringBody("""{"destination":"test${n}","locationX":${n},"locationY":${n},"username":"${passenger}"}"""))
      .check(status.is(200), jsonPath("$.id").saveAs("tripId"))
    ).exec(http("PayTrip-API")
      .put("http://localhost:8090/trips/payment/${tripId}")
      .header("Content-Type", "application/json")
      .check(status.is(200))
    )
//      .pause(FiniteDuration.apply(10, TimeUnit.MILLISECONDS))
  }

  setUp(scn.inject(atOnceUsers(20))).maxDuration(FiniteDuration.apply(5, TimeUnit.MINUTES))
//    .assertions(global.responseTime.max.lt(100))

}
