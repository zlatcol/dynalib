package models

case class Review (
	id: Int,
	bookId: Int,
	userId: Int,
	score: Int,
	comment: String
)
