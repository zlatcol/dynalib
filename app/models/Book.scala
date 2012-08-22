package models
import java.util.Date
import controllers.AuthorController
import controllers.CategoryController
import play.api.libs.json._
import controllers.CopyController

case class Book (
	val id: Int,
	var title: String,
	var language: String,
	var pages: Int
) {
	lazy val authors = AuthorController.getByBookId(this.id)

	lazy val categories = CategoryController.getByBookId(this.id)
	
	lazy val borrowedCopies = CopyController.getAllBorrowedCopiesForBook(this.id)
}