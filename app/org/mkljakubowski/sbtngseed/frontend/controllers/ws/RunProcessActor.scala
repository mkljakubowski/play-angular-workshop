package org.mkljakubowski.sbtngseed.frontend.controllers.ws

import akka.actor.{ Actor, ActorLogging, ActorRef, Terminated }
import play.api.Logger
import play.api.libs.json.Json

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Random

class RunProcessActor()(implicit ec: ExecutionContext) extends Actor with ActorLogging {

  var actors = Set.empty[ActorRef]
  var statuses = Seq.empty[Long]
  var running = false
  var stop = false

  def start(): Unit = {
    if (!running) {
      running = true
      stop = false
      statuses = Seq.empty

      Future {
        while (!stop) {
          self ! Random.nextInt().toLong
          Thread.sleep(500)
        }
        running = false
      }

    }
  }

  def receive = {
    case status: Long =>
      statuses = statuses :+ status
      actors.foreach(_ ! Json.toJson(status))
    case RunProcessActor.Stop =>
      Logger.debug(s"stop calculations")
      stop = true
    case recipient: ActorRef =>
      actors += recipient
      context.watch(recipient)
      start()
      statuses.foreach(status => recipient ! Json.toJson(status))
    case Terminated(recipient) =>
      actors -= recipient
  }
}

object RunProcessActor {

  case object Stop

}
