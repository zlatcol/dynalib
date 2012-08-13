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
	
	def addBookToAuthor(bookId: Int, authorId: Int) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO book_author (bookId, authorId) VALUES ({bId}, {aId})").on('bId -> bookId, 'aId -> authorId).executeInsert()
		}
	}
	
	def addAuthor(author: Author): Option[Long] = {
		DB.withConnection( implicit c =>
			SQL("INSERT INTO authors (name) VALUES ({name})").on('name -> author.name).executeInsert()
		)
	}
}