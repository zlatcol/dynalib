package controllers

import play.api._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.mvc._
import models.Book
import play.api.data._
import play.api.data._
import play.api.data.Forms._
import models.User
import java.util.Date
import play.api.cache.Cache

object BookController extends Controller {
	
	/**
	 * Skapa Book objekt, skicka till DBn.
	 */
	def addBook(book: Book): Int = {
		var id = DB.withConnection { implicit c =>
			SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeInsert()
		}.getOrElse(0)
		killListCache
		Integer.parseInt(id.toString())
	}
	
	def getBookById(id: Int): Option[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages, borrowed_by, date_back FROM books WHERE id = {id}").on('id -> id).as(bookParser.singleOpt)
		}
	}
	
	val bookParser = 
  		get[Int]("id")~
  		get[String]("title")~
  		get[String]("language")~
  		get[Int]("pages")~
  		get[Option[String]]("borrowed_by")~
  		get[Option[Date]]("date_back") map {
  			case id~title~language~pages~borrowed_by~date_back => new Book(id, title, language, pages, borrowed_by, date_back)
  		}
 
	def getAllBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages, borrowed_by, date_back FROM books").as(bookParser *)
		}
	}
	
	def getAllAvailableBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages, borrowed_by, date_back FROM books WHERE date_back IS NULL").as(bookParser *)
			
		}
	}
	
	def getAllBorrowedBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages, borrowed_by, date_back FROM books WHERE date_back IS NOT NULL").as(bookParser *)
		}
	}
	
	def borrowBook(bookId: Int, userId: Int, days: Int) {
		val user = UserController.getUserById(userId).getOrElse[User](new User(0, "Error"))
		killListCache
		DB.withConnection { implicit c =>
			SQL("UPDATE books SET borrowed_by = {borrowed_by}, date_back = date_add(DATE(NOW()), INTERVAL "+days+" DAY) WHERE id = {id}").on('id -> bookId, 'borrowed_by -> user.name).executeUpdate()
		}
	}
	
	def returnBook(bookId: Int) {
		killListCache
		DB.withConnection { implicit c =>
			SQL("UPDATE books SET borrowed_by = NULL, date_back = NULL WHERE id = {id}").on('id -> bookId).executeUpdate()
		}
	}
	
	def killListCache() {
		Cache.set("allBorrowed", None)
		Cache.set("allBooks", None)
		Cache.set("allAvailable", None)
	}
}