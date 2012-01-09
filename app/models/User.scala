package models

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.{AuthenticationException, UsernamePasswordToken}

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import security.StringPasswordEncryptor

/**
 *
 * @author wsargent
 * @since 1/8/12
 */

case class User(email: String, password: String)

object User {

  val passwordEncryptor = StringPasswordEncryptor

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("user.email") ~/
      get[String]("user.password") ^^ {
      case email~password => User(email, password)
    }
  }

  def findByEmail(email: String): Option[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from user where email = {email}").on(
          'email -> email
        ).as(User.simple ?)
    }
  }

  def findAll: Seq[User] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from user").as(User.simple *)
    }
  }

  def authenticate(email: String, password: String): Boolean = {
    // Use shiro to pass through a username password token.
    val token = new UsernamePasswordToken(email, password)
    //token.setRememberMe(true)

    val currentUser = SecurityUtils.getSubject
    try {
      currentUser.login(token)
      true
    } catch {
      case e: AuthenticationException => false
    }
  }

  def logout() {
    SecurityUtils.getSubject.logout()
  }


  def register(email: String, password: String): Boolean = {
    findByEmail(email) match {
      case None => {
        create(User(email, password))
        true
      }
      case _ => false
    }
  }

  def create(user: User): User = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            insert into user values (
              {email}, {password}
            )
          """
        ).on(
          'email -> user.email,
          'password -> passwordEncryptor.encryptPassword(user.password)
        ).executeUpdate()
        user
    }
  }


}