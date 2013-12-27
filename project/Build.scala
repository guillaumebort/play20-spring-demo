import sbt._
import play.Project._
import sbt.Keys._

object ApplicationBuild extends Build {

    val appName         = "play-spring"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.springframework" % "spring-context" % "4.0.0.RELEASE"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
