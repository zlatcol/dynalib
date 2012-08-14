package models
import play.api.data._
import play.api.data.Forms._

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
	
	/** Borrow book formuläret **/
	val borrowBookForm = Form(
		tuple(
			"bookId" -> number,
			"userId" -> number
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

}