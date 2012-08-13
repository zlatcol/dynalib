package models
import play.api.data._
import play.api.data.Forms._

object AuthorHelper {

	val addAuthorForm = Form(
		tuple(
			"id" -> number,
			"name" -> nonEmptyText
		)
	)
	
	/** Felmeddelanden **/
	val error_addAuthorForm = "Something went wrong"
	
	def getAddedMsg(name: String): String = {
		return "Author: "+name+" added"
	}

}