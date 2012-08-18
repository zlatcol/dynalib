package models

case class User (
	val id: Int,
	val email: String,
	var name: String
)

object User {

}