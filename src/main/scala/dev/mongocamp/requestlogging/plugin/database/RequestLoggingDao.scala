package dev.mongocamp.requestlogging.plugin.database

import dev.mongocamp.driver.mongodb.MongoDAO
import dev.mongocamp.requestlogging.plugin.listener.DatabaseRequestLoggingElement
import dev.mongocamp.server.database.MongoDatabase

case class RequestLoggingDao() extends MongoDAO[DatabaseRequestLoggingElement](MongoDatabase.databaseProvider, RequestLoggingDatabase.CollectionNameRequestLog)
