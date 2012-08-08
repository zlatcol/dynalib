package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	
	val bookParser = get[String]("title") map {case title => new Book(title)}
	
	def getTitles(): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT title FROM books").as(bookParser *)
			
		}
		
	}

}