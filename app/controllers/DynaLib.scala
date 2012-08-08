package controllers

import play.api._

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models.Book
import models.Author

object DynaLib extends Controller {
	def listAllBooks = Action {
		val list = BookController.getAllBooks()
		Ok(views.html.allBooks(list))
	}
}