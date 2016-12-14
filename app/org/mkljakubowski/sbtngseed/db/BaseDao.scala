package org.mkljakubowski.sbtngseed.db

import org.mkljakubowski.sbtngseed.frontend.controllers.QueryRequest
import slick.ast.BaseTypedType
import slick.dbio
import slick.driver.JdbcProfile

import scala.concurrent._
import scala.concurrent.duration.Duration

trait BaseDao[Entity] {

  implicit val ec: ExecutionContext
  protected val driver: JdbcProfile
  protected val db: JdbcProfile#Backend#Database
  import driver.api._

  implicit class SyncInvokeDBIOAction[+R, +S <: dbio.NoStream, -E <: dbio.Effect](action: DBIOAction[R, S, E]) {
    def invokeAction: Future[R] = db.run(action)
  }

  def table: TableQuery[_ <: Table[Entity]]

  def findAll(): Future[Seq[Entity]] = table.result.invokeAction

}

trait BaseIdDao[Entity <: BaseIdEntity[Id], T <: BaseTable, Id] extends BaseDao[Entity] {

  import driver.api._

  implicit val tt: BaseTypedType[Id]

  def table: TableQuery[_ <: T#BaseIdTable[Entity, Id] with Table[Entity]]

  def saveQuery: driver.ReturningInsertActionComposer[Entity, Option[Id]]

  def save(row: Entity): Future[Id] = saveQuery.insertOrUpdate(row).invokeAction.map(_.flatten.getOrElse(row.id.get))

  def search(obj: QueryRequest): Future[Seq[Entity]] = {
    val sth = table.baseTableRow.create_*.map(_.name)
      .flatMap { columnName => obj.obj.value.find { _._1.toLowerCase == columnName.toLowerCase }.map(v => columnName -> v._2.as[String]) }
    table.filter { r =>
      sth.foldLeft(LiteralColumn(true): Rep[Boolean]) {
        case (a, (name, value)) =>
          a && r.column[String](name.toUpperCase) === value
      }
    }.drop(obj.skip).take(obj.take).result.invokeAction
  }

  def count(): Future[Int] = table.size.result.invokeAction

  def findById(id: Id): Future[Option[Entity]] = table.filter(_.id === id).result.headOption.invokeAction

  def createSchema(): Unit = Await.result(table.schema.create.invokeAction, Duration.Inf)

}
