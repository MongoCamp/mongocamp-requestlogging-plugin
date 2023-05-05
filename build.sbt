import dev.quadstingray.sbt.json.JsonFile

val json = JsonFile(file("package.json"))

name := json.stringValue("name")

organization := json.stringValue("organization")

scalaVersion := "2.13.10"

libraryDependencies += "dev.mongocamp" %% "mongocamp-server" % "1.4.2"

credentials += Credentials("GitHub Package Registry", "maven.pkg.github.com", System.getenv("GITHUB_USER"), System.getenv("GITHUB_TOKEN"))

mainClass := Some("dev.mongocamp.server.Server")
