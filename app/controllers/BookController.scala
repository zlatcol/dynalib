package controllers

import play.api._
import play.api.mvc._
import models.Book

object BookController {
	def getAllBooks(): List[Book] = {
		return DBHandler.getTitles()
	}
}