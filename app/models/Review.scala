package models

case class Review (
	val id: Int,
	val bookId: Int,
	val userId: Int,
	val score: Int,
	val comment: String
)