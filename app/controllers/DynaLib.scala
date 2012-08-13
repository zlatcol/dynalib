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
	
	def listAvailableBooks = Action {
		val list = BookController.getAllAvailableBooks
		Ok(views.html.allBooks(list))
	}
	
	def listBorrowedBooks = Action {
		val list = BookController.getAllBorrowedBooks
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
	
	/** Tar emot borrow book requesten. Gör anrop för att ändra i DBn vilken book som är utlånad och till vem. **/
	def handleBorrowBookRequest = Action { implicit request =>
		BookHelper.borrowBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			borrowBookForm => {
				val bookId = borrowBookForm._1
				val userId = borrowBookForm._2
				BookController.borrowBook(bookId, userId)
				val list = BookController.getAllBooks
				Ok(views.html.allBooks(list))
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
		val users = UserController.getUsers
		Ok(views.html.book(book, authors, users))
	}
}