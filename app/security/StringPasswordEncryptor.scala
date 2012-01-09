package security

import org.jasypt.digest._
import org.jasypt.digest.config._
import org.jasypt.util.password.PasswordEncryptor

/**
 * A password encryptor that passes through to the string digester on the backend.
 */
object StringPasswordEncryptor extends PasswordEncryptor
{

  private def generateStringDigester : StringDigester = {
    val d = new StandardStringDigester()
    val config = new SimpleDigesterConfig()
    d.setConfig(config)
    d
  }

  lazy val stringDigester = generateStringDigester

  def checkPassword(message: String, digest: String) = stringDigester.matches(message, digest)

  def encryptPassword(p1: String) = stringDigester.digest(p1)
}