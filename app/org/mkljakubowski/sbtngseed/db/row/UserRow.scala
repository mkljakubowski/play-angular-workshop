package org.mkljakubowski.sbtngseed.db.row

import org.mkljakubowski.sbtngseed.db.BaseIdEntity
import play.api.libs.json.Json

case class UserRow(id: Option[Int], firstName: String, lastName: String, age: Int) extends BaseIdEntity[Int]

object UserRow {
  implicit val format = Json.format[UserRow]
}