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
import models.BookHelper

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
		BookHelper.addBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.addBook(BookHelper.addBookForm, BookHelper.error_addBookForm)),
			bookForm => {
				val book = new Book(bookForm._1, bookForm._2, bookForm._3, bookForm._4)
				val bookId = BookController.addBook(book)
				AuthorController.addBookToAuthor(bookId, bookForm._5)
				Ok(views.html.addBook(BookHelper.addBookForm, BookHelper.getAddedMsg(bookForm._2)))
			}
		)
	}
	
	/**
	 * Visa addBook vyn
	 */
	def addBook = Action {
		Ok(views.html.addBook(BookHelper.addBookForm))
	}
	
	/** 
	 * Hämta Bokinfo och skicka till book vyn
	 */
	def book(id: Int) = Action {
		val book = BookController.getBook(id)
		val authors = AuthorController.getAuthorByBookId(id)
		Ok(views.html.book(book, authors))
	}
}