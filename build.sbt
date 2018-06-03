name := "Daedalus"

version := "0.1"

scalaVersion := "2.12.6"

resolvers ++= Seq(
  "zalando-maven" at "https://dl.bintray.com/zalando/maven"
)

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= {
  Seq(
    "io.monix" %% "monix" % "2.3.3",
    "org.json4s" %% "json4s-native" % "3.6.0-M3",
    "org.slf4j" % "slf4j-ext" % "1.7.10",
    "org.slf4j" % "slf4j-simple" % "1.7.10",
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.mockito" % "mockito-core" % "2.7.19" % Test,
    "com.rabbitmq" % "amqp-client" % "5.2.0",
    "org.typelevel" %% "cats-core" % "1.1.0",
    "de.zalando" %% "beard" % "0.2.0",
    "io.github.cloudify" %% "spdf" % "1.4.0",
    "org.typelevel" %% "cats-core" % "1.1.0"
  )
}