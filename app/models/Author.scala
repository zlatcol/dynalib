package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Author (
	val id: Int,
	var name: String
)

object Author {
	
	val authorParser = 
  		get[Int]("id")~
  		get[String]("name") map {
  			case id~name => new Author(id, name)
  		}
	
	val authors = List[(String, Int)](
			("France",1),
			("England",2)
		)
		
	def getAuthors: List[Author] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, name FROM authors").as(authorParser *)
			
		}
	}
	
	def getByBookId(id: Int): List[Author] = {
		DB.withConnection { implicit c =>
			SQL("SELECT authors.id, authors.name FROM authors WHERE id IN (SELECT authorId FROM book_author WHERE bookId = {id})").on('id -> id).as(authorParser *)
		}
	}
}