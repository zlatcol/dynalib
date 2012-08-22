package models

import play.api.data._
import play.api.data.Forms._

object CopyHelper {
	
	val addCopyForm = Form(
			"bookId" -> number
	)
	
	val returnBookForm = Form(
		tuple(
			"id" -> number, 
			"bookId" -> number
		)
	)

}