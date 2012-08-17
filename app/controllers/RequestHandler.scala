package controllers
import play.api._
import scala.collection.mutable.ListBuffer
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models.Book
import models.Author
import models.BookHelper
import models.AuthorHelper
import play.api.cache.Cache
import models.SearchHelper
import models.Book

object RequestHandler extends Controller{
	
	def handleAddBookRequest = Action { implicit request =>
		BookHelper.addBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.addBook(BookHelper.addBookForm, BookHelper.error_addBookForm)),
			bookForm => {
				val book = new Book(bookForm._1, bookForm._2, bookForm._3, bookForm._4)
				val bookId = BookController.addBook(book)
				
				val authors = request.body.asFormUrlEncoded.get("author")
				
				for (author <- authors) {
					AuthorController.addBookToAuthor(bookId, Integer.parseInt(author))
				}
				Ok(views.html.addBook(BookHelper.addBookForm, BookHelper.getAddedMsg(bookForm._2)))
			}
		)
	}
	
	/**
	 * Tar emot requesten från addAuthor viewn och skickar det vidare till AuthorControllern
	 */
	def handleAddAuthorRequest = Action { implicit request =>
		AuthorHelper.addAuthorForm.bindFromRequest.fold(
			errors => BadRequest(views.html.addAuthor(AuthorHelper.addAuthorForm, AuthorHelper.error_addAuthorForm)),
			authorForm => {
				val author = new Author(authorForm._1, authorForm._2)
				val authorId = AuthorController.addAuthor(author)
				var msg = ""
				if (authorId > 0) {
					msg = AuthorHelper.getAddedMsg(authorForm._2)
				} else {
					msg = "Adding author failed"
				}
				Ok(views.html.addAuthor(AuthorHelper.addAuthorForm, msg))
			}
		)
	}
	
	/** Tar emot borrow book requesten. Gör anrop för att ändra i DBn vilken book som är utlånad och till vem. **/
	def handleBorrowBookRequest = Action { implicit request =>
		BookHelper.borrowBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			borrowBookForm => {
				val bookId = borrowBookForm._1
				val userId = borrowBookForm._2
				val days = borrowBookForm._3
				BookController.borrowBook(bookId, userId, days)
				Redirect(routes.DynaLib.book(bookId))
			}
		)
	}
	
	/** Tar emot return book requesten. Gör anrop mot DBn som sätter att boken är tillbaka och dylikt **/
	def handleReturnBookRequest = Action { implicit request =>
		BookHelper.returnBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			returnBookForm => {
				val bookId = returnBookForm
				BookController.returnBook(bookId)
				Redirect(routes.DynaLib.book(bookId))
			}
		)
	}
	
	def handleSearchByAuthor = Action { implicit request =>
		SearchHelper.authorSearchForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			authorSearchForm => {
				val authorId = authorSearchForm
				val searchResults = SearchController.searchByAuthor(authorId)
				Ok(views.html.results(searchResults))
			}
		)
	}
	
	def handleEditBookRequest = Action {implicit request =>
		BookHelper.bookIdForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			id => {
				val book = BookController.getBookById(id)
				val authors = AuthorController.getAuthorByBookId(id)
				var tempAuthors = ListBuffer[Int]()
				authors.foreach(author => tempAuthors+= author.id)
				val authorIds = tempAuthors.toList
				
				Ok(views.html.editBook(book, authors, authorIds))
			}
		)
	}
	
	def handlePerfomEditRequest = Action { implicit request =>
		BookHelper.editBookForm.bindFromRequest.fold(
			errors => BadRequest(views.html.index()),
			form => {
				val authors = request.body.asFormUrlEncoded.get("author").toList				
				val book = new Book(form._1, form._2, form._3, form._4)
				BookController.editBook(book, authors)
				Redirect(routes.DynaLib.book(form._1))
			}
		)
	}

}