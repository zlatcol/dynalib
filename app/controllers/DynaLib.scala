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
import models.AuthorHelper

object DynaLib extends Controller {
	
	def index() = Action {
		Ok(views.html.index())
	}
	
	/** 
	 *	Självförklarande funktion. Kommentar onödig.
	 */
	def listAllBooks = Action {
		val list = BookController.getAllBooks
		Ok(views.html.allBooks(list))
	}
			
	/** 
	 * Tar emot requesten från addBook viewn och skickar det vidare till BookControllern
	 */
	def handleAddBookRequest = Action { implicit request =>
		
		val authors = request.body.asFormUrlEncoded.get("author")
		request.body.asFormUrlEncoded.drop(0)
		BookHelper.addBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.addBook(BookHelper.addBookForm, BookHelper.error_addBookForm)),
			bookForm => {
				val book = new Book(bookForm._1, bookForm._2, bookForm._3, bookForm._4)
				val bookId = BookController.addBook(book)
				for (author <- authors) {
					AuthorController.addBookToAuthor(bookId, Integer.parseInt(author))
				}
				Ok(views.html.addBook(BookHelper.addBookForm, BookHelper.getAddedMsg(bookForm._2)))
			}
		)
	}
	
	/**
	 * Tar emot requesten från addAuthor viewn och skickar det vidare till AuthorControllern
	 */
	def handleAddAuthorRequest = Action { implicit request =>
		AuthorHelper.addAuthorForm.bindFromRequest.fold(
			errors => BadRequest(views.html.addAuthor(AuthorHelper.addAuthorForm, AuthorHelper.error_addAuthorForm)),
			authorForm => {
				val author = new Author(authorForm._1, authorForm._2)
				val authorId = AuthorController.addAuthor(author)
				var msg = ""
				if (authorId > 0) {
					msg = AuthorHelper.getAddedMsg(authorForm._2)
				} else {
					msg = "Adding author failed"
				}
				Ok(views.html.addAuthor(AuthorHelper.addAuthorForm, msg))
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
	 * Visa addAuthor vyn
	 */
	def addAuthor = Action {
		Ok(views.html.addAuthor(AuthorHelper.addAuthorForm))
	}
	
	
	/** 
	 * Hämta Bokinfo och skicka till book vyn
	 */
	def book(id: Int) = Action {
		val book = BookController.getBookById(id)
		val authors = AuthorController.getAuthorByBookId(id)
		Ok(views.html.book(book, authors))
	}
}