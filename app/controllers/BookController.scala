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
	def addBook(bookInfo: (String, String, Int)) {
		DBHandler.addBook(bookInfo)
	}
	
	def getBook(id: Int): Option[Book] = {
		Book.getById(id)
	}
}