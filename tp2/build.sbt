enablePlugins(ScalaJSPlugin)

name := "bus-frequency"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "rolodato" %%% "genetics" % "0.1.0-SNAPSHOT",
  "org.scala-js" %%% "scalajs-dom" % "0.8.0"
)

scalacOptions ++= List("-feature","-deprecation", "-unchecked", "-Xlint")
