package org.mkljakubowski.sbtngseed.frontend.controllers

import jsmessages.JsMessagesFactory
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc._
import play.api.routing._

class ApplicationController(
    val messagesApi: MessagesApi
) extends Controller with I18nSupport {

  lazy val jsMessagesFactory = new JsMessagesFactory(messagesApi)
  lazy val jsMessages = jsMessagesFactory.all

  lazy val messages = Action { implicit request =>
    Ok(jsMessages(Some("jsMessages")))
  }

  lazy val javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.DummyController.run,
        routes.javascript.DummyController.stop,
        routes.javascript.UserController.save,
        routes.javascript.UserController.get,
        routes.javascript.UserController.findAll,
        routes.javascript.UserController.search
      )
    ).as("text/javascript")
  }

}
