package models

import controllers.AuthorController
import controllers.CategoryController
import controllers.CopyController
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import anorm.~

case class Book (
	id: Int,
	var title: String,
	var language: String,
	var pages: Int
) {
	lazy val authors = AuthorController.getByBookId(this.id)

	lazy val categories = CategoryController.getByBookId(this.id)

	lazy val borrowedCopies = CopyController.getAllBorrowedCopiesForBook(this.id)
}

object Book {
  /**
   * The columns to query when selecting a book
   */
  val bookColumns = """
		books.id,
		books.title,
		books.language,
		books.pages
    """

  val bookParser =
    get[Int]("id") ~
      get[String]("title") ~
      get[String]("language") ~
      get[Int]("pages") map {
      case id ~ title ~ language ~ pages => {
        new Book(id, title, language, pages)
      }
    }

  def findById(id: Int): Option[Book] = {
    DB.withConnection {
      implicit connection =>
        SQL("SELECT " + this.bookColumns + " FROM books WHERE id = {id}").on('id -> id).as(bookParser.singleOpt)
    }
  }
}
