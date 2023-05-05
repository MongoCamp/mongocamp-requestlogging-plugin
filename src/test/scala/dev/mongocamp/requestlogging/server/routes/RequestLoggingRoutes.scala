package dev.mongocamp.requestlogging.plugin

import dev.mongocamp.server.exception.ErrorDescription
import dev.mongocamp.server.model.JsonValue
import dev.mongocamp.server.plugin.RoutesPlugin
import dev.mongocamp.server.route.BaseRoute
import io.circe.generic.auto._
import sttp.capabilities.WebSockets
import sttp.capabilities.akka.AkkaStreams
import sttp.model.{Method, StatusCode}
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.server.ServerEndpoint

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

object RequestLoggingRoutes extends BaseRoute with RoutesPlugin {

  val blockingTestRoute = baseEndpoint.tag("TestEndpoint")
    .in("blocking")
    .out(jsonBody[JsonValue[Boolean]])
    .summary("Test Request")
    .description("Test Request with 5s blocking")
    .method(Method.GET)
    .name("blocking")
    .serverLogic(_ => blockingMethod())

  def blockingMethod(): Future[Either[(StatusCode, ErrorDescription, ErrorDescription), JsonValue[Boolean]]] = {
    Future.successful(Right({
      Thread.sleep(5.seconds.toMillis)
      JsonValue(true)
    }))
  }

  override def endpoints: List[ServerEndpoint[AkkaStreams with WebSockets, Future]] = {
    List(blockingTestRoute)
  }
}
