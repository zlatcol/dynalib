package models

import play.api.data._
import play.api.data.Forms._

object SearchHelper {
	
	val authorSearchForm = Form(
		"authorId" -> number
	)
	
	val categorySearchForm = Form(
		"categoryId" -> number
	)

}