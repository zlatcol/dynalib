package controllers

import play.api._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.mvc._
import models.Book
import play.api.data._
import play.api.data._
import play.api.data.Forms._
import models.User
import java.util.Date
import play.api.cache.Cache
import traits.Secured
import models.Copy

object CopyController {
	
	val copyParser = 
  		get[Int]("id")~
  		get[Int]("bookId")~
  		get[Option[Int]]("borrowed_by")~
  		get[Option[Date]]("date_borrowed")~
  		get[Option[Date]]("date_back") map {
  			case id~bookId~borrowedBy~dateBorrowed~dateBack => {
  				val user = borrowedBy.map { userid =>
  					UserController.getUserById(userid)
  				}.getOrElse { Option.empty }
  				new Copy(id, bookId, user, dateBorrowed, dateBack)  				  
  			}
		}
	
	def addCopyForBook(bookId: Int) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO copies (bookId) VALUES ({bookId})").on('bookId -> bookId).executeInsert()
		}
	}
	
	//Se till att man lånar en kopia av en book. Spara en koppling till vilken bok i copiesdbn.
	def borrowCopy(bookId: Int, userId: Int, days: Int) {
		UserController.getUserById(userId).map { user =>
			val freeCopy = DB.withConnection {implicit c => SQL("SELECT id AS copyID FROM copies WHERE bookId = {bookId} AND borrowed_by IS NULL LIMIT 1").on('bookId -> bookId).apply().head
			}
			
			val res = DB.withConnection { implicit c =>
				SQL("UPDATE copies SET borrowed_by = {borrowed_by}, date_borrowed = now(), date_back = DATE(NOW()) + INTERVAL '"+days+" days' WHERE bookId = {bookId} AND id = {copyId}").on('bookId -> bookId, 'borrowed_by -> user.id, 'copyId -> Integer.parseInt(freeCopy[Long]("copyId").toString())).executeUpdate()
			}
			BookController.killListCache()
			res  
		}.getOrElse {
			0
		}
	}
	
	def getAllBorrowedCopiesForBook(bookId: Int): List[Copy] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, bookId, borrowed_by, date_back, date_borrowed FROM copies WHERE bookId = {bookId} AND borrowed_by IS NOT NULL").on('bookId -> bookId).as(copyParser *)
		}
	}
	
	//Returnera en kopia. Behövs bookId?
	def returnBook(copyId: Int) {
		DB.withConnection { implicit c =>
			SQL("UPDATE copies SET borrowed_by = NULL, date_borrowed = NULL, date_back = NULL WHERE id = {id}").on('id -> copyId).executeUpdate()
		}
		BookController.killListCache()
	}
	
	def getNumberOfAvailableCopiesForBook(bookId: Int): Int = {
		val result = DB.withConnection { implicit c =>
			SQL("SELECT count(*) AS count FROM copies WHERE bookId = {bookId} AND borrowed_by IS NULL").on('bookId -> bookId).apply().head
		}
		Integer.parseInt(result[Long]("count").toString())
	}
	
	def deleteCopy(bookId: Int) {
		DB.withConnection { implicit c =>
			SQL("DELETE FROM copies WHERE bookId = {bookId}").on('bookId -> bookId).execute()
		}
	}

}
