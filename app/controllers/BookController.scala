package controllers

import play.api._
import play.api.mvc._
import models.Book
import play.api.data._
import play.api.data._
import play.api.data.Forms._

object BookController extends Controller {
	
	def getAllBooks(): List[Book] = {
		return DBHandler.getTitles()
	}
	
	/**
	 * Skapa Book objekt, skicka till DBn.
	 */
	def addBook(bookInfo: (String, String, Int)) {
		val book = new Book(bookInfo._1, bookInfo._2 ,bookInfo._3)
		DBHandler.addBook(book)
	}
}