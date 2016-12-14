package org.mkljakubowski.sbtngseed.frontend.controllers

import akka.actor.ActorRef
import org.mkljakubowski.sbtngseed.db.dao.UserDao
import org.mkljakubowski.sbtngseed.db.row.UserRow
import org.mkljakubowski.sbtngseed.frontend.ApplicationConfiguration
import play.api.libs.json.Format
import play.api.mvc.Controller

import scala.concurrent.ExecutionContext

class UserController(
  applicationConfiguration: ApplicationConfiguration,
  val dao:                  UserDao,
  master:                   ActorRef
)(implicit val ec: ExecutionContext)
    extends Controller
    with BaseController[UserRow] {

  override implicit val format: Format[UserRow] = UserRow.format

}
