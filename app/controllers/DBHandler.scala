package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	
	def addBook(book: Book) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO books (title, language, pages) VALUES ({title}, {language}, {pages})").on('title -> book.title, 'language -> book.language, 'pages -> book.pages).executeUpdate()
		}
	}
}