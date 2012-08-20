package controllers
import models.Book
import play.api.db.DB
import models.BookHelper
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object SearchController {
	
	def searchByAuthor(authorId: Int): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages, borrowed_by, date_borrowed, date_back FROM books WHERE id IN(SELECT book_author.bookId FROM book_author WHERE authorId = {aId})").on('aId -> authorId).as(BookController.bookParser *)
		}
	}
	
	def searchByCategory(categoryId: Int): List[Book] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, title, language, pages, borrowed_by, date_borrowed ,date_back FROM books WHERE id IN(SELECT book_category.bookId FROM book_category WHERE categoryId = {cId})").on('cId -> categoryId).as(BookController.bookParser *)
		}
	}

}