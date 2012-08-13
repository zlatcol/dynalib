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

object BookController extends Controller {
	
	/**
	 * Skapa Book objekt, skicka till DBn.
	 */
	def addBook(book: Book): Int = {
		var id = DB.withConnection { implicit c =>
			SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeInsert()
		}.getOrElse(0)
		Integer.parseInt(id.toString())
	}
	
	def getBookById(id: Int): Option[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages FROM books WHERE id = {id}").on('id -> id).as(bookParser.singleOpt)
		}
	}
	
	val bookParser = 
  		get[Int]("id")~
  		get[String]("title")~
  		get[String]("language")~
  		get[Int]("pages") map {
  			case id~title~language~pages => new Book(id, title, language, pages)
  		}
 
	def getAllBooks: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages FROM books").as(bookParser *)
			
		}
	}
}