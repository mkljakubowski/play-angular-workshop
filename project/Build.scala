import sbt.Keys._
import sbt._

object Build extends sbt.Build with Settings {

  val project = Project(id = "sbt-angular-require", base = file("."))
    .enablePlugins(play.sbt.PlayScala)
    .settings(globalBuildSettings)
    .settings(serverCompileSettings)

}
