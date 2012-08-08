package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	
	val bookParser = get[String]("title")~get[Int]("pages") map {case title~pages => new Book(title,pages)}
	
	def getTitles(): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT title, pages FROM books").as(bookParser *)
			
		}
		
	}

}