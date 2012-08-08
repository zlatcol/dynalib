package controllers

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object DBHandler {
	
	val intParser = get[String]("result")~get[String]("operation") map {case id~operation => (id, operation)}
	
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
	}
	
	def mysql() {
		
	}

}