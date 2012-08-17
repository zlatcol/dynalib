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
import play.api.libs.openid.OpenID
import play.api.libs.concurrent.Redeemed
import play.api.libs.concurrent.Thrown
import traits.Secured

object DynaLib extends Controller with Secured {

	def index() = withAuth {
		email => Action {
			Ok(views.html.index("Welcome to DynaLib: "+email))
		}
	}
	
	/** 
	 *	Självförklarande funktion. Kommentar onödig.
	 */
	def listAllBooks = withAuth { 
		email => Action {
			val list: List[Book] = Cache.getOrElse[List[Book]]("allBooks", 600) {
				BookController.getAllBooks
			}
			Ok(views.html.allBooks("All Books", list))
		}
	}
	
	def listAvailableBooks = withAuth { 
		email => Action {
			val list: List[Book] = Cache.getOrElse[List[Book]]("allAvailable", 600) {
				BookController.getAllAvailableBooks
			}
			Ok(views.html.allBooks("Available Books", list))
		}
	}
	
	def listBorrowedBooks = withAuth { 
		email => Action {
			val list: List[Book] = Cache.getOrElse[List[Book]]("allBorrowed", 600) {
				BookController.getAllBorrowedBooks
			}
			Ok(views.html.allBooks("Borrowed Books", list))
		}
	}
	
	def search = withAuth { 
		email => Action {
			Ok(views.html.search(SearchHelper.authorSearchForm, SearchHelper.categorySearchForm))
		}
	}

	/**
	 * Visa addBook vyn
	 */
	def addBook = withAuth { 
		email => Action {
			Ok(views.html.addBook(BookHelper.addBookForm))
		}
	}
	
	/**
	 * Visa addAuthor vyn
	 */
	def addAuthor = withAuth { 
		email => Action {
			Ok(views.html.addAuthor(AuthorHelper.addAuthorForm))
		}
	}

	/** 
	 * Hämta Bokinfo och skicka till book vyn
	 */
	def book(id: Int) = withAuth { 
		email => Action {
			val book = BookController.getBookById(id)
			val authors = AuthorController.getAuthorByBookId(id)
			val categories = CategoryController.getCategoryByBookId(id)
			val users = UserController.getUsers
			Ok(views.html.book(book, authors, categories, users))
		}
	}
}