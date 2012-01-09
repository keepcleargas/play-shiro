import models.User
import play.api._

import org.apache.shiro.mgt.DefaultSecurityManager
import security.{StringPasswordEncryptor, SampleRealm}

/**
 *
 * @author wsargent
 * @since 1/8/12
 */

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    ShiroConfig.initialize()
    InitialData.insert()
  }

}

object ShiroConfig {

  def initialize() {
    val sampleRealm = new SampleRealm()
    val securityManager = new DefaultSecurityManager()
    securityManager.setRealm(sampleRealm)
    org.apache.shiro.SecurityUtils.setSecurityManager(securityManager)
  }

}


/**
 * Initial set of data to be imported
 * in the sample application.
 */
object InitialData {

  def insert() = {

    if(User.findAll.isEmpty) {

      Seq(
        User("admin@example.com", "admin")
      ).foreach(User.create)
    }

  }

}