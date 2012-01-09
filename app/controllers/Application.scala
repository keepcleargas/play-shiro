package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import models.User

object Application extends Controller with Authentication {
  
  def index = Action { implicit request =>
    Ok(views.html.index(User.findAll, currentUser))
  }
  
  def logout = Action { implicit request =>
    User.logout()
    Redirect(routes.Application.index).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }
  
}


trait Authentication {

  /**
   * Retrieve the connected user email.
   */
  def authToken(request: RequestHeader) = request.session.get("email")

  def currentUser(implicit request: RequestHeader) : Option[User] = authToken(request).flatMap { User.findByEmail(_) }

}
