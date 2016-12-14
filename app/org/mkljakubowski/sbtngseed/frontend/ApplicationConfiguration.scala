package org.mkljakubowski.sbtngseed.frontend

import play.api._

class ApplicationConfiguration(configuration: Configuration) {

  lazy val environment = configuration.getString("env").getOrElse("ERROR_ENV")
  lazy val title = s"$appName - $environment"
  lazy val appName = configuration.getString("play.application.name").getOrElse("Unknown application")

}
