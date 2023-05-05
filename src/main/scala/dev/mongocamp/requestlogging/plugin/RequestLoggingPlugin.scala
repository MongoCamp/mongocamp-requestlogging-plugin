package dev.mongocamp.requestlogging.plugin

import akka.actor.Props
import com.typesafe.scalalogging.LazyLogging
import dev.mongocamp.requestlogging.plugin.listener.{DatabaseRequestLoggingElement, RequestLoggingActor}
import dev.mongocamp.server.database.MongoDatabase
import dev.mongocamp.server.event.EventSystem
import dev.mongocamp.server.event.http.HttpRequestEvent
import dev.mongocamp.server.model.MongoCampConfiguration
import dev.mongocamp.server.plugin.ServerPlugin
import dev.mongocamp.server.service.ConfigurationService
import org.mongodb.scala.bson.codecs.Macros._

object RequestLoggingPlugin extends ServerPlugin with LazyLogging {
  lazy val ConfigKeyRequestLogging           = "REQUESTLOGGING_ENABLED"
  
  override def activate(): Unit = {
    ConfigurationService.registerConfig(ConfigKeyRequestLogging, MongoCampConfiguration.confTypeBoolean, needsRestartForActivation = true)
    MongoDatabase.addToProvider(classOf[DatabaseRequestLoggingElement])
    if (ConfigurationService.getConfigValue(ConfigKeyRequestLogging)) {
      val requestLoggingActor = EventSystem.eventBusActorSystem.actorOf(Props(classOf[RequestLoggingActor]), "requestLoggingActor")
      EventSystem.eventStream.subscribe(requestLoggingActor, classOf[HttpRequestEvent])
    }
  }


}
