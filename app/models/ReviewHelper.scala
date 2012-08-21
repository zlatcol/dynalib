package models

import play.api.data._
import play.api.data.Forms._
import controllers.ReviewController

object ReviewHelper {

	val reviewForm = Form(
		tuple(
			"bookId" -> number,
			"userId" -> number,
			"score" -> number,
			"comment" -> text
		)
	)
	
}