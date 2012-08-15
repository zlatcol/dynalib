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
import play.api.cache.Cache
import models.SearchHelper

object DynaLib extends Controller {
	
	def index() = Action {
		//A comment. 
		Ok(views.html.index())
	}
	
	/** 
	 *	Självförklarande funktion. Kommentar onödig.
	 */
	def listAllBooks = Action {
		
		val list: List[Book] = Cache.getOrElse[List[Book]]("allBooks", 600) {
			BookController.getAllBooks
		}
		Ok(views.html.allBooks("All Books", list))
	}
	
	def listAvailableBooks = Action {
		
		val list: List[Book] = Cache.getOrElse[List[Book]]("allAvailable", 600) {
			BookController.getAllAvailableBooks
		}
		Ok(views.html.allBooks("Available Books", list))
	}
	
	def listBorrowedBooks = Action {
		val list: List[Book] = Cache.getOrElse[List[Book]]("allBorrowed", 600) {
			BookController.getAllBorrowedBooks
		}
		Ok(views.html.allBooks("Borrowed Books", list))
	}
	
	def search = Action {
		Ok(views.html.search(SearchHelper.authorSearchForm, SearchHelper.categorySearchForm))
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
		val categories = CategoryController.getCategoryByBookId(id)
		val users = UserController.getUsers
		Ok(views.html.book(book, authors, categories, users))
	}
}