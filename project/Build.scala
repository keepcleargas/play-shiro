import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play-shiro"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "org.jasypt" % "jasypt" % "1.7",
      "org.apache.shiro" % "shiro-core" % "1.1.0"
      // Add your project dependencies here,
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
