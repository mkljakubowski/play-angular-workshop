package org.mkljakubowski.sbtngseed.server

import akka.actor.{ ActorSystem, Props }
import com.softwaremill.macwire._
import org.mkljakubowski.sbtngseed.db.dao.UserDao
import org.mkljakubowski.sbtngseed.frontend.ApplicationConfiguration
import org.mkljakubowski.sbtngseed.frontend.controllers.ws.RunProcessActor
import play.api._
import play.api.cache.CacheApi
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

trait MainApplicationModule
    extends HasDatabaseConfig[JdbcProfile] {

  implicit def ec: ExecutionContext

  def configuration: Configuration
  def defaultCacheApi: CacheApi
  def actorSystem: ActorSystem

  lazy val userDao = new UserDao(driver, db, defaultCacheApi)

  lazy val config = wire[ApplicationConfiguration]

  lazy val master = actorSystem.actorOf(Props(wire[RunProcessActor]))

}
