package org.mkljakubowski.sbtngseed

import org.mkljakubowski.sbtngseed.server.MainApplicationModule
import org.specs2.mock.Mockito
import play.api.ApplicationLoader.Context
import play.api.db.slick.DbName
import play.api.test.{PlaySpecification, WithApplicationLoader}
import play.api.{Application, ApplicationLoader, Configuration}
import slick.driver.JdbcProfile

/**
  * Created by jakum on 2016-12-14.
  */
trait BaseTest extends PlaySpecification with Mockito {

  var pr: TestComponents = _

  class TestComponents(context: Context) extends AppComponents(context) {
    override lazy val dbConfig = api.dbConfig[JdbcProfile](DbName("test"))
    override lazy val configuration = context.initialConfiguration ++ Configuration(
      "slick.dbs.test.driver" -> "slick.driver.H2Driver$",
      "slick.dbs.test.db.url" -> "jdbc:h2:mem:test"
    )

    userDao.createSchema()
  }

  private class IntegrationContext extends WithApplicationLoader(
    applicationLoader = new ApplicationLoader {
    override def load(context: Context): Application = {
      pr = new TestComponents(context)
      pr.application
    }
  }
  )

  def integrationContext(test: MainApplicationModule => Unit) = {
    val ic = new IntegrationContext
    ic.around {
      test(pr)
      ok
    }
  }

}
