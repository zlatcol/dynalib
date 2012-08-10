package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	
	val idParser = get[Int]("id") map (id => id)
	
	def addBook(book: Book): Option[Long] =  {
		DB.withConnection { implicit c =>
			val id: Option[Long] = SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeInsert()
			return id
		}
	}
}