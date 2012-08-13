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
	
<<<<<<< HEAD
	/** Return book formuläret **/
=======
>>>>>>> Return books
	val returnBookForm = Form(
		"bookId" -> number
	)
	
<<<<<<< HEAD
	/** Filter book formuläret **/
	val filterBooksForm = Form(
		"language" -> nonEmptyText
	)
	
=======
>>>>>>> Return books
	/** Felmeddelanden **/
	val error_addBookForm = "Something went wrong"
	
	def getAddedMsg(title: String): String = {
		return "Book: "+title+" added"
	}

}