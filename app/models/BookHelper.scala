package models
import play.api.data._
import play.api.data.Forms._

object BookHelper {
	/** Add book formuläret **/
	val addBookForm = Form(
		mapping(
			"id" -> number,
			"title" -> nonEmptyText,
			"language" -> nonEmptyText,
			"pages" -> number(min = 0)
		)(Book.apply)(Book.unapply)
	)
	
	/** Felmeddelanden **/
	val error_addBookForm = "Something went wrong"
	
	def getAddedMsg(title: String): String = {
		return "Book: "+title+" added"
	}

}