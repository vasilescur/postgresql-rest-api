import org.scoverage.coveralls.CoverallsPlugin.coverallsSettings
import scoverage.ScoverageSbtPlugin._

name := "pg-rest-api"

version := "0.1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:dynamics"
)


play.Project.playScalaSettings ++ instrumentSettings ++ coverallsSettings

ScoverageKeys.excludedPackages in ScoverageCompile := "<empty>;Reverse.*;views.html.*"



