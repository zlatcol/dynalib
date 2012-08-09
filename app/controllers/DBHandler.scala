package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	
	val bookParser = get[Int]("id")~get[String]("title")~get[String]("language")~get[Int]("pages") map {case id~title~language~pages => new Book(id,title,language,pages)}
	
	def getTitles(): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id,title, language, pages FROM books").as(bookParser *)
			
		}
	}
	
	def addBook(book: List[(String, String, Int)]) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book(0), 'language -> book(1), 'pages -> book(3)).executeUpdate()
		}
	}
	
	
	def getBook(id: Int): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id,title,language,pages FROM books WHERE id = {id}").on('id -> id).as(bookParser *)
		}
	}

}