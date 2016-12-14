package org.mkljakubowski.sbtngseed.db.table

import org.mkljakubowski.sbtngseed.db.BaseTable
import org.mkljakubowski.sbtngseed.db.row.UserRow

trait UserTable
    extends BaseTable {

  import driver.api._

  class UserRows(tag: Tag) extends BaseIdTable[UserRow, Int](tag, "USERS") {

    def firstName = column[String]("FIRSTNAME")
    def lastName = column[String]("LASTNAME")

    def * = (id, firstName, lastName) <> ((UserRow.apply _).tupled, UserRow.unapply)
  }

}
