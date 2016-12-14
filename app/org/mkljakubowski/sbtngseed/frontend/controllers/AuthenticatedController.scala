package org.mkljakubowski.sbtngseed.frontend.controllers

import play.api.libs.json.{ JsError, Json, Reads, Writes }
import play.api.mvc._

import scala.concurrent.{ ExecutionContext, Future }

case class User(username: String)

case class Context[T](request: Request[T], user: User)

trait AuthenticatedController extends Controller {

  implicit val ec: ExecutionContext

  implicit def contextToUser[T](implicit context: Context[T]) = context.user

  implicit def contextToRequest[T](implicit context: Context[T]) = context.request

  private def userInfo(request: RequestHeader) = for {
    name <- request.session.get(Security.username)
  } yield User(name)

  private def authenticated(action: User => EssentialAction): EssentialAction = Security.Authenticated(userInfo, onUnauthorized)(action)

  private def onUnauthorized(request: RequestHeader): Result = Redirect(routes.AuthenticationController.login())

  def withAuth(fun: Context[AnyContent] => Result): EssentialAction = {
    authenticated { implicit user =>
      Action.async { implicit request =>
        val context = Context(request, User(user.username))
        val result = fun(context)
        Future.successful(result)
      }
    }
  }

  def withAuthAsync(fun: Context[AnyContent] => Future[Result]): EssentialAction = {
    authenticated { implicit user =>
      Action.async { implicit request =>
        val context = Context(request, User(user.username))
        val result = fun(context)
        result
      }
    }
  }

  def withoutAuth(fun: Context[AnyContent] => Result): EssentialAction = {
    Action.async { implicit request =>
      val context = Context(request, User(""))
      val result = fun(context)
      Future.successful(result)
    }
  }

  def doPost[Req, Res](f: Req => Future[Res])(implicit reqReads: Reads[Req], resWrites: Writes[Res]): EssentialAction = authenticated { implicit user =>
    Action.async(parse.json) { request =>
      val entryJsResult = request.body.validate[Req]
      entryJsResult.fold(
        errors => Future.successful(BadRequest(JsError.toJson(errors))),
        entry => f(entry).map(res => Ok(Json.toJson(res)))
      )
    }
  }

  def doGet[Res](f: () => Future[Res])(implicit resWrites: Writes[Res]): EssentialAction = authenticated { implicit user =>
    Action.async { request =>
      f().map(res => Ok(Json.toJson(res)))
    }
  }

  def doGetOption[Res](f: Future[Option[Res]])(implicit resWrites: Writes[Res]): EssentialAction = authenticated { implicit user =>
    Action.async { request =>
      f.map {
        resOpt => resOpt.map { res => Ok(Json.toJson(res)) }.getOrElse(NotFound)
      }
    }
  }

}
