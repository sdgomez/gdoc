name := "bestPractices"

organization := "example"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.10.4", "2.11.2")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.5.7",
  "com.typesafe.akka" %% "akka-http" % "10.0.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.5",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.5" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.6",
  "com.typesafe.slick"    %%    "slick"              %     "3.2.1",
  "com.typesafe.slick"    %%    "slick-hikaricp"     %     "3.2.1",
  "com.zaxxer"            %     "HikariCP"           %     "2.6.3",
  "com.typesafe"          %     "config"             %     "1.3.1",
  "com.h2database"        %     "h2"                 %     "1.4.196"       %   Test,
  "org.postgresql" % "postgresql" % "42.1.3",
  "org.typelevel" %% "cats-core" % "1.0.0-RC2"
)

initialCommands := "import example._"
