package dev.mongocamp.requestlogging.plugin

import dev.mongocamp.driver.mongodb._
import dev.mongocamp.requestlogging.client.api.TestEndpointApi
import dev.mongocamp.requestlogging.plugin.database.RequestLoggingDatabase
import dev.mongocamp.server.test.{MongoCampBaseServerSuite, TestAdditions}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}

class TestLoggingSuite extends MongoCampBaseServerSuite {

  val api: TestEndpointApi = TestEndpointApi()

  test("check a request is logged as 'new' and acknowledge") {
    val testFuture = TestAdditions.backend.send(api.blocking())
    Thread.sleep(1.seconds.toMillis)
    val results = RequestLoggingDatabase.requestLoggingDao.find(Map("methodName" -> "blocking", "duration" -> -1), Map("date" -> -1)).resultList()
    assertEquals(results.size, 1)
    val testRequestResponse = Await.result(testFuture, Duration.Inf)
    val results2 = RequestLoggingDatabase.requestLoggingDao.find(Map("methodName" -> "blocking"), Map("date" -> -1)).resultList()
    assertEquals(results2.size, 1)
    assertEquals(results.head.requestId, results2.head.requestId)
    assertEquals(results2.head.duration > 0, true)
    assertEquals(testRequestResponse.header("x-request-id").getOrElse("unknown"), results.head.requestId)
  }

}
