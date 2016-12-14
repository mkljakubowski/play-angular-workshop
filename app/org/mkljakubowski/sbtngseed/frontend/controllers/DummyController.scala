package org.mkljakubowski.sbtngseed.frontend.controllers

import akka.actor.{ ActorRef, ActorSystem }
import akka.stream.Materializer
import org.mkljakubowski.sbtngseed.frontend.views.html
import org.mkljakubowski.sbtngseed.db.dao.UserDao
import org.mkljakubowski.sbtngseed.frontend.ApplicationConfiguration
import org.mkljakubowski.sbtngseed.frontend.controllers.ws.{ RunProcessActor, WebSocketActor }
import play.api.libs.json.{ JsValue, Json }
import play.api.libs.streams.ActorFlow
import play.api.mvc.{ Controller, WebSocket }

import scala.concurrent.{ ExecutionContext, Future }

class DummyController(
  applicationConfiguration: ApplicationConfiguration,
  userDao:                  UserDao,
  master:                   ActorRef
)(implicit system: ActorSystem, materializer: Materializer, val ec: ExecutionContext)
    extends Controller
    with AuthenticatedController {

  def indexAny(any: String) = withAuth { implicit context =>
    Ok(html.index(applicationConfiguration))
  }

  def index = indexAny("")

  def run() = WebSocket.accept[JsValue, JsValue] { request =>
    ActorFlow.actorRef(out => WebSocketActor.props(out, master))
  }

  def stop() = withAuthAsync { implicit context =>
    master ! RunProcessActor.Stop
    Future.successful(Ok(Json.toJson("stopped")))
  }

}
