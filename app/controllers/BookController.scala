package controllers

import play.api._
import play.api.mvc._
import models.Book
import play.api.data._
import play.api.data._
import play.api.data.Forms._

object BookController extends Controller {
	
	def getAllBooks(): List[Book] = {
		Book.getAll
	}
	
	/**
	 * Skapa Book objekt, skicka till DBn.
	 */
	def addBook(book: Book): Int = {
		val id = Book.addBook(book).getOrElse(0)
		Integer.parseInt(id.toString())
	}
	
	def getBook(id: Int): Option[Book] = {
		Book.getById(id)
	}
}