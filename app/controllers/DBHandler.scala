package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

import models.Book

object DBHandler {
	/** Old junk **/ 
	/*val intParser = get[String]("result")~get[String]("operation") map {case id~operation => (id, operation)}
	
	def saveResultInDb(result: Any, operation: String) {
		DB.withConnection { implicit c =>
			SQL("insert into results (result, operation) values ({result}, {operation})").on(
				'result -> result,
				'operation -> operation
			).executeUpdate()
		}
	}
	
	def getAllResults(): List[(String, String)] = {
		DB.withConnection { implicit c =>
			SQL("select result, operation from results").as(intParser *)
		}
	}*/

	val bookParser = get[String]("title") map {case title => new Book(title)}
	
	def getTitles(): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT title FROM books").as(bookParser *)
			
		}
		
	}

}