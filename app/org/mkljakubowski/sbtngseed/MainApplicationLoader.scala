package org.mkljakubowski.sbtngseed

import _root_.controllers.Assets
import com.softwaremill.macwire._
import org.mkljakubowski.sbtngseed.frontend.controllers.{ ApplicationController, AuthenticationController, DummyController, UserController }
import org.mkljakubowski.sbtngseed.server.MainApplicationModule
import play.api.ApplicationLoader.Context
import play.api.cache.EhCacheComponents
import play.api.db.evolutions.{ DynamicEvolutions, EvolutionsComponents }
import play.api.db.slick.evolutions.SlickEvolutionsComponents
import play.api.db.slick.{ DbName, HasDatabaseConfig, SlickComponents }
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.api.{ LoggerConfigurator, _ }
import router.Routes
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

class MainApplicationLoader
    extends ApplicationLoader {

  def load(context: Context) = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }

    val app = new AppComponents(context)
    app.applicationEvolutions.start()
    app.application
  }

}

trait DatabaseModule
    extends SlickComponents
    with SlickEvolutionsComponents
    with EvolutionsComponents
    with HasDatabaseConfig[JdbcProfile] {

  implicit val ec: ExecutionContext = play.api.libs.concurrent.Execution.Implicits.defaultContext
  lazy val dbConfig = api.dbConfig[JdbcProfile](DbName("default"))
  val dynamicEvolutions = new DynamicEvolutions

}

class AppComponents(
  context: Context
) extends BuiltInComponentsFromContext(context)
    with DatabaseModule
    with I18nComponents
    with MainApplicationModule
    with EhCacheComponents {

  implicit val as = actorSystem

  lazy val dummyController = wire[DummyController]
  lazy val applicationController = wire[ApplicationController]
  lazy val userController = wire[UserController]
  lazy val authenticationController = wire[AuthenticationController]
  lazy val assets: Assets = wire[Assets]
  lazy val prefix = "/"
  lazy val router: Router = wire[Routes]

}
