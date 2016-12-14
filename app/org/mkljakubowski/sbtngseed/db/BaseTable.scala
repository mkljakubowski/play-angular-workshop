package org.mkljakubowski.sbtngseed.db

import java.sql.{ Date, Timestamp }

import org.joda.time.{ DateTime, LocalDate, LocalTime, Period }
import slick.ast.TypedType
import slick.driver.JdbcProfile

trait BaseIdEntity[Id] {
  def id: Option[Id]
}

trait BaseTable {

  protected val driver: JdbcProfile

  import driver.api._

  implicit val localDateTypedType = MappedColumnType.base[LocalDate, Date](
    { d => new Date(d.toDateTime(LocalTime.MIDNIGHT).getMillis) },
    { d => new LocalDate(d.getTime) }
  )

  implicit val booleanTypedType = MappedColumnType.base[Boolean, Int](
    { bool => if (bool) { 1 } else { 0 } },
    { _ == 1 }
  )

  implicit val periodTypedType = MappedColumnType.base[Period, Int](
    { bool => bool.getMillis },
    { new Period(_) }
  )

  implicit val dateTypedType = MappedColumnType.base[DateTime, Timestamp](
    { d => new Timestamp(d.getMillis) },
    { d => new DateTime(d.getTime) }
  )

  abstract class BaseIdTable[T <: BaseIdEntity[Id], Id](tag: Tag, name: String)(implicit tt: TypedType[Id])
      extends Table[T](tag, name) {
    def id = column[Option[Id]]("ID", O.PrimaryKey, O.AutoInc)
  }

}
