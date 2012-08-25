package models

import controllers.AuthorController
import controllers.CategoryController
import controllers.CopyController

case class Book (
	id: Int,
	var title: String,
	var language: String,
	var pages: Int
) {
	lazy val authors = AuthorController.getByBookId(this.id)

	lazy val categories = CategoryController.getByBookId(this.id)
	
	lazy val borrowedCopies = CopyController.getAllBorrowedCopiesForBook(this.id)
}
