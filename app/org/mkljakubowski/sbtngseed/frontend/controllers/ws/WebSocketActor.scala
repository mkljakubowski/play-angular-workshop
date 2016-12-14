package org.mkljakubowski.sbtngseed.frontend.controllers.ws

import akka.actor.{ Actor, ActorRef, Props }
import play.api.libs.json.JsValue

class WebSocketActor(out: ActorRef, master: ActorRef) extends Actor {

  master ! self

  def receive = {
    case msg: JsValue =>
      out ! msg
  }
}

object WebSocketActor {
  def props(out: ActorRef, master: ActorRef) = Props(new WebSocketActor(out, master))
}
