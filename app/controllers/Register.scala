package controllers

import views.html

import models._

import play.api.mvc._
import play.api.data._

/**
 *
 * @author wsargent
 * @since 1/8/12
 */

object Register extends Controller {


  val registerForm = Form(
    of(
      "email" -> text,
      "password" -> text
    ) verifying("Cannot register user", result => result match {
      case (email, password) => User.register(email, password)
    })
  )

  def index = Action(implicit request =>
    Ok(html.register(registerForm))
  )

  def register = Action {
    implicit request =>
      val result = registerForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(html.login(formWithErrors))
        },
        success => {
          Redirect(routes.Application.index).withSession("email" -> success._1)
        }
      )
      result.asInstanceOf[Result]
  }
}