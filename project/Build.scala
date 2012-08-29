import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "dynalib"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "postgresql" % "postgresql" % "8.4-702.jdbc4",
      "org.mockito" % "mockito-all" % "1.9.5-rc1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here
    )

}
