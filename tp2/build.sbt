name := "bus-frequency"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "rolodato" % "genetics_2.11" % "0.1.0-SNAPSHOT"
)

scalacOptions ++= List("-feature","-deprecation", "-unchecked", "-Xlint")
