import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play-spring"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.springframework" % "spring-context" % "3.2.3.RELEASE"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
	// Add your own project settings here
	//    templatesImport += "com.abc.backend._"
	//    templatesImport += "views.components._"

      // Add your own project settings here      
    )

}
