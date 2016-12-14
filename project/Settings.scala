import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import play.sbt.PlayImport.PlayKeys._
import play.sbt.PlayImport._
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys._

import scalariform.formatter.preferences.{AlignArguments, AlignParameters, AlignSingleLineCaseStatements}

trait Settings extends Dependencies {

  val compileSettings = Seq(
    organization := "org.mkljakubowski.sbtngseed",
    scalaVersion := "2.11.8",
    scalacOptions := Seq(
      "-language:postfixOps",
      "-language:reflectiveCalls",
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xlint",
      "-Xfuture",
      "-Yno-adapted-args"
    ),
    javacOptions ++= Seq("-g", "-deprecation", "-source", "1.8", "-target", "1.8", "-encoding", "UTF-8"),
    publishArtifact in Test := true,
    version := "1.0.0",
    logLevel := Level.Info,
    crossPaths := false
  )

  val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(AlignArguments, true)
      .setPreference(AlignParameters, true)
  )


  val serverCompileSettings = Seq(
    packagedArtifacts in publishLocal := {
      val artifacts = (packagedArtifacts in publishLocal).value
      val assets = (playPackageAssets in Compile).value
      artifacts + (Artifact(moduleName.value, "jar", "jar", "assets") -> assets)
    },
    packagedArtifacts in publish := {
      val artifacts = (packagedArtifacts in publish).value
      val assets = (playPackageAssets in Compile).value
      artifacts + (Artifact(moduleName.value, "jar", "jar", "assets") -> assets)
    },
    libraryDependencies ++= serverDependencies,
    parallelExecution in Test := false,
    logBuffered := false,
    coverageEnabled in Test := true,
    coverageOutputHTML := true,
    coverageMinimum := 75,
    coverageFailOnMinimum := true
  )

  val serverDependencies = Seq(
    jdbc,
    cache,
    specs2 % Test,
    jsMessages
  ) ++ macwire ++ playSlick ++ webjars

  val globalBuildSettings = formatSettings ++ compileSettings

}
