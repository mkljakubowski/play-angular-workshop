package org.mkljakubowski.sbtngseed.frontend.controllers

import org.mkljakubowski.sbtngseed.frontend.views.html
import org.mkljakubowski.sbtngseed.frontend.ApplicationConfiguration
import org.mkljakubowski.sbtngseed.frontend.controllers.model.LoginForm
import play.api.data.Form
import play.api.mvc.{ Action, Controller, RequestHeader }

import scala.concurrent.ExecutionContext

class AuthenticationController(
  applicationConfiguration: ApplicationConfiguration
)(implicit ec: ExecutionContext)
    extends Controller {

  private def loginPage(form: Form[LoginForm])(implicit request: RequestHeader) = {
    Ok(html.login(form.globalError.map(_.message), applicationConfiguration))
  }

  def login = Action { implicit request =>
    loginPage(Form(LoginForm._mapping))
  }

  def authenticate = Action { implicit request =>
    val form = Form(LoginForm._mapping)
    form.bindFromRequest.fold(
      errors => loginPage(errors),
      formData => {
        Redirect(routes.DummyController.index()).addingToSession("username" -> formData.username)
      }
    )
  }

  def logout = Action { implicit request =>
    Redirect(routes.AuthenticationController.login()).withNewSession
  }

}
