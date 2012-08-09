package models
import play.api.data._
import play.api.data.Forms._


object BookForm {
	/** Add book formuläret **/
	val addBookForm = Form(
		tuple(
			"title" -> text,
			"pages" -> number
		)
	)

}