package org.mkljakubowski.sbtngseed.frontend.controllers.model

import play.api.data.Forms._

case class LoginForm(
  username: String,
  password: String
)

object LoginForm {
  implicit val _mapping = mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginForm.apply)(LoginForm.unapply)
}
