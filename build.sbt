name := """bodega-v2"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
)

play.Project.playScalaSettings