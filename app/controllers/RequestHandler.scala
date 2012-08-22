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
import traits.Secured
import models.User
import models.Review
import models.CopyHelper

object RequestHandler extends Controller with Secured {
	
	def handleAddBookRequest = withUser {
		implicit user => Action { implicit request =>
			BookHelper.addBookForm.bindFromRequest.fold(
				errors => BadRequest(views.html.addBook(BookHelper.addBookForm, BookHelper.error_addBookForm)),
				bookForm => {
					val book = new Book(bookForm._1, bookForm._2, bookForm._3, bookForm._4)
					val bookId = BookController.addBook(book)

					val formParams = request.body.asFormUrlEncoded.getOrElse(Map())
					val authors = formParams.getOrElse("author", List())
					val categories = formParams.getOrElse("category", List())
					
					for (author <- authors) {
						AuthorController.addBookToAuthor(bookId, Integer.parseInt(author))
					}
					for (category <- categories) {
						CategoryController.addCategoryToBook(bookId, Integer.parseInt(category))
					}
					Ok(views.html.addBook(BookHelper.addBookForm, BookHelper.getAddedMsg(bookForm._2)))
				}
			)
		}
	}
	
	/**
	 * Tar emot requesten från addAuthor viewn och skickar det vidare till AuthorControllern
	 */
	def handleAddAuthorRequest = withUser {
		implicit user => Action { implicit request =>
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
	}
	
	/** Tar emot borrow book requesten. Gör anrop för att ändra i DBn vilken book som är utlånad och till vem. **/
	def handleBorrowBookRequest = withUser {
		implicit user => Action { implicit request =>
			BookHelper.borrowBookForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				borrowBookForm => {
					val bookId = borrowBookForm._1
					val userId = borrowBookForm._2
					val days = borrowBookForm._3
					if (user.id == userId) {
						CopyController.borrowCopy(bookId, userId, days)
					}
					Redirect(routes.DynaLib.book(bookId))
				}
			)
		}
	}
	
	def handleReviewBookRequest = withUser {
		implicit user => Action {
			implicit request => {
				val bookId = Integer.parseInt(request.body.asFormUrlEncoded.get("bookId").head)
				val userId = Integer.parseInt(request.body.asFormUrlEncoded.get("userId").head)
				val score = Integer.parseInt(request.body.asFormUrlEncoded.get("RadioGroup").head)
				val comment = request.body.asFormUrlEncoded.get("comment").head
				
				val review = new Review(0,bookId,userId,score,comment)
				ReviewController.addReview(review)
				Redirect(routes.DynaLib.listAllBooks)
			}
		}
	}
	
	/** Tar emot return book requesten. Gör anrop mot DBn som sätter att boken är tillbaka och dylikt **/
	def handleReturnBookRequest = withUser {
		implicit user => Action { implicit request =>
			CopyHelper.returnBookForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				returnBookForm => {
					val id = returnBookForm._1
					val bookId = returnBookForm._2
					CopyController.returnBook(id)
					Redirect(routes.DynaLib.book(bookId))
				}
			)
		}
	}
	
	def handleSearchByAuthor = withUser {
		implicit user => Action { implicit request =>
			SearchHelper.authorSearchForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				authorSearchForm => {
					val authorId = authorSearchForm
					val searchResults = SearchController.searchByAuthor(authorId)
					Ok(views.html.allBooks("Search Results:", searchResults))
				}
			)
		}
	}
	
	def handleSearchByCategory = withUser {
		implicit user => Action { implicit request =>
			SearchHelper.categorySearchForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				categorySearchForm => {
					val categoryId = categorySearchForm
					val searchResults = SearchController.searchByCategory(categoryId)
					Ok(views.html.allBooks("Search Results:", searchResults))
				}
			)
		}
	}
	
	def handleEditBookRequest = withUser {
		implicit user => Action { implicit request =>
			BookHelper.bookIdForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				id => {
					val book = BookController.getBookById(id)
					
					Ok(views.html.editBook(book))
				}
			)
		}
	}
	
	def handlePerfomEditRequest = withUser {
		implicit user => Action { implicit request =>
			BookHelper.editBookForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				form => {
					val formParams = request.body.asFormUrlEncoded.getOrElse(Map())
					val authors = formParams.getOrElse("author", List()).toList
					val categories = formParams.getOrElse("category", List()).toList
					
					val book = new Book(form._1, form._2, form._3, form._4)
					BookController.editBook(book, authors, categories)
					Redirect(routes.DynaLib.book(form._1))
				}
			)
		}
	}

	def handleSaveUserRequest = withUser {
		implicit user => Action { implicit request =>
			User.editUserForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				form => {
					if (user.id == form._1) {
						UserController.changeName(form._1, form._2)
					}
					Redirect(routes.DynaLib.user(form._1))
				}
			)
		}
	}
	
	def handleAddCopyRequest = withUser {
		implicit user => Action { implicit request =>
			CopyHelper.addCopyForm.bindFromRequest.fold(
				errors => BadRequest(views.html.index()),
				form => {
					CopyController.addCopyForBook(form)
					Redirect(routes.DynaLib.book(form))
				}
			)
		}
	}
	
}