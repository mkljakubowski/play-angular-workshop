package org.mkljakubowski.sbtngseed.db.dao

import org.mkljakubowski.sbtngseed.db.BaseIdDao
import org.mkljakubowski.sbtngseed.db.row.UserRow
import org.mkljakubowski.sbtngseed.db.table.UserTable
import play.api.cache.CacheApi
import slick.ast.BaseTypedType
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext

class UserDao(
  val driver: JdbcProfile,
  val db:     JdbcProfile#Backend#Database,
  cache:      CacheApi
)(implicit val ec: ExecutionContext)
    extends BaseIdDao[UserRow, UserTable, Int]
    with UserTable {

  import driver.api._

  lazy val table = TableQuery[UserRows]
  lazy val saveQuery = table returning table.map(_.id)
  override implicit val tt = implicitly[BaseTypedType[Int]]

  //  table.schema.createStatements.foreach(println)

}
