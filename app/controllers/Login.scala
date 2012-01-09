package controllers

import views.html

import play.api._
import play.api.mvc._
import play.api.data._

import models.User

/**
 *
 * @author wsargent
 * @since 1/8/12
 */

object Login extends Controller {

  // -- Authentication

  val loginForm = Form(
    of(
      "email" -> email,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => User.authenticate(email, password)
    })
  )

  /**
   * Login page.
   */
  def index = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    val result = loginForm.bindFromRequest.fold(
      formWithErrors => { BadRequest(html.login(formWithErrors)) },
      success => { Redirect(routes.Application.index).withSession("email" -> success._1) }
    )
    result.asInstanceOf[Result]
  }


}