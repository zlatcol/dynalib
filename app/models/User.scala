package models

import play.api.data.Forms._
import play.api.data.Form

case class User (
	id: Int,
	email: String,
	var name: String
)

object User {
	val editUserForm = Form(
		tuple(
			"id" -> number,
			"name" -> nonEmptyText
		)
	)
}
