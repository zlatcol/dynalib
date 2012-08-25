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
import traits.Secured

object BookController extends Controller with Secured {

	/**
	 * The columns to query when selecting a book
	 */
	val bookColumns = """
		books.id,
		books.title,
		books.language,
		books.pages
	""";

	/**
	 * Skapa Book objekt, skicka till DBn.
	 */
	def addBook(book: Book): Int = {
		var id = DB.withConnection { implicit c =>
			SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeInsert()
		}.getOrElse(0)
		CopyController.addCopyForBook(Integer.parseInt(id.toString()))
		killListCache
		Integer.parseInt(id.toString())
	}
	
	def getBookById(id: Int): Option[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT "+this.bookColumns+" FROM books WHERE id = {id}").on('id -> id).as(bookParser.singleOpt)
		}
	}
	
	val bookParser = 
  		get[Int]("id")~
  		get[String]("title")~
  		get[String]("language")~
  		get[Int]("pages") map {
  			case id~title~language~pages => {
  				new Book(id, title, language, pages)  				  
  			}
		}
 
	def getAllBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT "+this.bookColumns+" FROM books").as(bookParser *)
		}
	}
	
	def getAllAvailableBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT "+this.bookColumns+" FROM books WHERE id IN (SELECT bookId FROM copies WHERE borrowed_by IS NULL GROUP BY bookId)").as(bookParser *)
			
		}
	}
	
	def getAllBorrowedBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT "+this.bookColumns+" FROM books WHERE id IN (SELECT bookId FROM copies WHERE borrowed_by IS NOT NULL GROUP BY bookId)").as(bookParser *)
		}
	}
	
	def getAllMyBooks(userId: Int): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT "+this.bookColumns+" FROM books WHERE id IN (SELECT bookId FROM copies WHERE borrowed_by = {id})").on('id -> userId).as(bookParser *)
		}
	}
	
	def editBook(book: Book, authors: List[String], categories: List[String]) {
		DB.withConnection { implicit c =>
			SQL("UPDATE books SET title = {title}, language = {language}, pages = {pages} WHERE id = {id}").on('id -> book.id, 'title -> book.title, 'language -> book.language, 'pages -> book.pages).executeUpdate()
			SQL("DELETE FROM book_author WHERE bookId = {id}").on('id -> book.id).executeUpdate()
			SQL("DELETE FROM book_category WHERE bookId = {id}").on('id -> book.id).executeUpdate()
			for(author <- authors) {
				SQL("INSERT INTO book_author (bookId,authorId) VALUES ({bookId},{authorId})").on('bookId -> book.id, 'authorId -> Integer.parseInt(author)).executeInsert()
			}
			for(category <- categories) {
				SQL("INSERT INTO book_category (bookId,categoryId) VALUES ({bookId},{categoryId})").on('bookId -> book.id, 'categoryId -> Integer.parseInt(category)).executeInsert()
			}
		}
	}
	
	def deleteBook(bookId: Int) {
		DB.withConnection { implicit c =>
			SQL("DELETE FROM books WHERE id = {id}").on('id -> bookId).execute()
		}
	}
	
	def killListCache {
			Cache.set("allBorrowed", None)
			Cache.set("allBooks", None)
			Cache.set("allAvailable", None)
	}
}
