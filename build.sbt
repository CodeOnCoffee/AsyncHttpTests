lazy val akkaHttpVersion = "10.2.4"
lazy val akkaVersion    = "2.6.14"

val username = scala.sys.env.getOrElse("ARTIFACTORY_USER", "You need a user" )
val password = scala.sys.env.getOrElse("ARTIFACTORY_PASS", "Refer to wiki for password" )

credentials := Seq(Credentials("Artifactory Realm", "tstllc.jfrog.io", username, password))
resolvers +=  "artifactory-releases" at "https://tstllc.jfrog.io/tstllc/releases/"
resolvers += Resolver.url(
  "artifactory-ivy-releases",
  url("https://tstllc.jfrog.io/tstllc/ivy-releases/")
)(Resolver.ivyStylePatterns)
//
//cinnamon in run := true
//cinnamon in test := true

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.10"
    )),
    name := "AsyncClientTestProject",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "org.slf4j" % "slf4j-api"    % "1.7.21" % "compile->default",
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",
      "ch.qos.logback" % "logback-classic" % "1.1.11",
      "ch.qos.logback" % "logback-core"    % "1.1.11",
      "net.tstllc" %% "common-mdc" % "2.0.+",
      "net.tstllc" %% "common-mdc-akka" % "2.0.+",
      "net.tstllc" %% "common-wsclients-play" % "2.0.+",
//      Cinnamon.library.cinnamonSlf4jMdc,
      "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test
    )
  )
