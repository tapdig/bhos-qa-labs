package PostgreSQLPostTest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PostSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:94.0) Gecko/20100101 Firefox/94.0")

  val scn = scenario("PostgreSQL Post Test")
    .feed(csv("mock_data.csv"))
    .exec(
      http("AddPost")
        // .post("/post")          // Test 1
        // .post("/postwithref")   // Test 2
        .post("/postandref")       // Test 3
        .formParam("title", "#{title}")
        .formParam("content", "#{content}")
        .formParam("referenceLinkURL", "#{referenceLinkURL}")
    )

  setUp(scn.inject(
    constantUsersPerSec(250).during(40.seconds)
  ).protocols(httpProtocol))
}