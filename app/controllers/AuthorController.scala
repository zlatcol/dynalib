package controllers

import play.api._
import play.api.mvc._
import models.Author
import play.api.data._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object AuthorController {
	
	val authorParser = 
  		get[Int]("id")~
  		get[String]("name") map {
  			case id~name => new Author(id, name)
  		}
	
	val countParser = get[Int]("count") map {case count => count}
	
	def addAuthor(author: Author): Int = {

		val result = DB.withConnection( implicit c =>
			SQL("INSERT INTO authors (name) VALUES ({name})").on('name -> author.name).executeInsert()
		).getOrElse(0)
		Integer.parseInt(result.toString())
	}
	
	def getAuthorByBookId(id: Int): List[Author] = {
		getByBookId(id)
	}
	
	def addBookToAuthor(bookId: Int, authorId: Int) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO book_author (bookId, authorId) VALUES ({bId}, {aId})").on('bId -> bookId, 'aId -> authorId).executeInsert()
		}
	}
	
	def getNumberOfAuthors(): Int = {
		val result = DB.withConnection ( implicit c =>
			SQL("SELECT count(id) AS count FROM authors").apply().head
		)
		Integer.parseInt(result[Long]("count").toString())
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
	
}