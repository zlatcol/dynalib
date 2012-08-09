package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	
	val bookParser = get[String]("title")~get[String]("language")~get[Int]("pages") map {case title~language~pages => new Book(title,language,pages)}
	
	def getTitles(): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT title, language, pages FROM books").as(bookParser *)
			
		}
	}
	
	def addBook(book: Book) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeUpdate()
		}
	}

}