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
		val a1 = new Author("Stephen King")
		val a2 = new Author("Hon som skrev Harry Potter")
		val b1 = new Book("Det", a1)
		val b2 = new Book("Harry Potter gör nått dumt", a2)
		val list = List[Book](b1,b2)
		
		Ok(views.html.allBooks(list))
	}
}