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
import models.BookForm

object DynaLib extends Controller {
	
	def index() = Action {
		Ok(views.html.index())
	}
	
	/** 
	 *	Självförklarande funktion. Kommentar onödig.
	 */
	def listAllBooks = Action {
		val list = BookController.getAllBooks()
		Ok(views.html.allBooks(list))
	}
			
	/** 
	 * Tar emot requesten från addBook viewn och skickar det vidare till BookControllern
	 * result = Tuple2(title, pages)
	 */
	def add = Action { implicit request =>
		BookForm.addBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			result => {
				val title = result._1
				val language = result._2
				val pages =result._3
				BookController.addBook(title, language ,pages)
				Ok(views.html.index())
			}
		)
	}
	
	/**
	 * Visa addBook vyn
	 */
	def addBook = Action {
		Ok(views.html.addBook(BookForm.addBookForm))
	}
}