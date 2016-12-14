import sbt._

trait Dependencies {

  val joda = Seq(
    "joda-time" % "joda-time" % "2.7" % Provided,
    "org.joda" % "joda-convert" % "1.7" % Provided
  )

  val macwire = Seq(
    "com.softwaremill.macwire" %% "macros" % "2.2.0" % Provided,
    "com.softwaremill.macwire" %% "util" % "2.2.0"
  )

  val playSlick = Seq(
    "com.h2database" % "h2" % "1.4.192",
    "com.typesafe.play" %% "play-slick" % "2.0.2",
    "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2"
  )

  val webjars = Seq(
    "org.webjars" % "jquery" % "2.2.4" % Provided,
    "org.webjars" % "bootstrap" % "3.3.6" % Provided,
    "org.webjars" % "bootstrap-datepicker" % "1.0.1" % Provided,
    "org.webjars" % "font-awesome" % "4.5.0" % Provided,
    "org.webjars" % "angularjs" % "1.6.0" % Provided,
    "org.webjars" % "requirejs" % "2.3.2" % Provided
  )

  val jsMessages = "org.julienrf" %% "play-jsmessages" % "2.0.0"

}
