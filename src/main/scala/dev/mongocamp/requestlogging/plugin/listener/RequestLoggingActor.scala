package dev.mongocamp.requestlogging.plugin.listener

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import dev.mongocamp.driver.mongodb._
import dev.mongocamp.requestlogging.plugin.database.RequestLoggingDatabase
import dev.mongocamp.server.BuildInfo
import dev.mongocamp.server.event.http.{ HttpRequestCompletedEvent, HttpRequestStartEvent }

import java.net.InetAddress

private[requestlogging] class RequestLoggingActor extends Actor with LazyLogging {
  def receive: Receive = {

    case "info" =>
      logger.info(this.getClass.getSimpleName)

    case event: HttpRequestCompletedEvent =>
      val updateMap      = Map("$set" -> Map("duration" -> event.duration.getMillis, "responseCode" -> event.responseCode))
      val updateResponse = RequestLoggingDatabase.requestLoggingDao.updateOne(Map("requestId" -> event.requestId), updateMap).result()
      updateResponse

    case event: HttpRequestStartEvent =>
      val requestLogging = DatabaseRequestLoggingElement(
        event.eventDate,
        BuildInfo.name,
        BuildInfo.version,
        InetAddress.getLocalHost.toString,
        event.requestId,
        event.httpMethod,
        event.methodName,
        event.uri,
        event.remoteAddress,
        event.userId,
        -1,
        -1,
        event.controller,
        event.controllerMethod,
        event.comment
      )
      val insertResponse = RequestLoggingDatabase.requestLoggingDao.insertOne(requestLogging).result()
      insertResponse
  }

}
