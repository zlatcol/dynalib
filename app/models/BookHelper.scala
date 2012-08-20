package models
import play.api.data._
import play.api.data.Forms._
import controllers.AuthorController

object BookHelper {
	/** Add book formuläret **/
	val addBookForm = Form(
		tuple(
			"id" -> number,
			"title" -> nonEmptyText,
			"language" -> nonEmptyText,
			"pages" -> number(min = 0),
			"author" -> number
		)
	)
	
	/** Edit book formuläret **/
	val editBookForm = Form(
		tuple(
			"id" -> number,
			"title" -> nonEmptyText,
			"language" -> nonEmptyText,
			"pages" -> number(min = 0)
		)
	)
	
	val bookIdForm = Form(
		"id" -> number
	)
	
	/** Borrow book formuläret **/
	val borrowBookForm = Form(
		tuple(
			"bookId" -> number,
			"userId" -> number,
			"days" -> number
		)
	)
	
	/** Return book formuläret **/
	val returnBookForm = Form(
		"bookId" -> number
	)
	
	/** Filter book formuläret **/
	val filterBooksForm = Form(
		"language" -> nonEmptyText
	)
	
	/** Felmeddelanden **/
	val error_addBookForm = "Something went wrong"
	
	def getAddedMsg(title: String): String = {
		return "Book: "+title+" added"
	}
	
	def getSizeOfMultiChoice(): Int = {
		var size = 0
		if(AuthorController.getNumberOfAuthors() > 10) {
			size = 10
		} else {
			size = AuthorController.getNumberOfAuthors()
		}
		size
	}

}