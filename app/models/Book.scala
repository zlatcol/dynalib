package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Book (
	val id: Int,
	var title: String,
	var language: String,
	var pages: Int
)

object Book {
	val bookParser = 
  		get[Int]("id")~
  		get[String]("title")~
  		get[String]("language")~
  		get[Int]("pages") map {
  			case id~title~language~pages => new Book(id, title, language, pages)
  		}

  	def getById(id: Int): Option[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages FROM books WHERE id = {id}").on('id -> id).as(bookParser.singleOpt)
		}
	}
 
	def getAll: List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages FROM books").as(bookParser *)
			
		}
	}
	
	val idParser = get[Int]("id") map (id => id)
	
	def addBook(book: Book): Option[Long] =  {
		DB.withConnection { implicit c =>
			val id: Option[Long] = SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeInsert()
			return id
		}
	}
}