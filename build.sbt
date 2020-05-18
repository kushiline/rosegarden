scalaVersion := "2.13.2"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies +=
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0"
libraryDependencies += 
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += 
  "ch.qos.logback" % "logback-classic" % "1.2.3"  

Compile / scalaSource := baseDirectory.value / "src"
Test / scalaSource := baseDirectory.value / "tst"
