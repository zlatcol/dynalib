package controllers


import play.api._
import play.api.mvc._
import models.Author
import play.api.data._

object AuthorController {
	
	
	def addAuthor(author: Author): Int = {
		val result = Author.addAuthor(author).getOrElse(0)
		Integer.parseInt(result.toString())
	}
	
	def getAuthorByBookId(id: Int): List[Author] = {
		Author.getByBookId(id)
	}
	
	def addBookToAuthor(bookId: Int, authorId: Int) {
		Author.addBookToAuthor(bookId, authorId)
	}

}