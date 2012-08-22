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

object CopyController {
	
	//Se till att man lånar en kopia av en book. Spara en koppling till vilken bok i copiesdbn.
	def borrowCopy(bookId: Int, userId: Int, days: Int) {
		UserController.getUserById(userId).map { user =>
			val res = DB.withConnection { implicit c =>
				//SQL("UPDATE books SET borrowed_by = {borrowed_by}, date_borrowed = now(), date_back = DATE(NOW()) + INTERVAL '"+days+" days' WHERE id = {id}").on('id -> bookId, 'borrowed_by -> user.id).executeUpdate()
			}
			res  
		}.getOrElse {
			0
		}
	}
	
	//Returnera en kopia. Behövs bookId?
	def returnBook(copyId: Int, bookId: Int) {
		DB.withConnection { implicit c =>
			//SQL("UPDATE books SET borrowed_by = NULL, date_borrowed = NULL, date_back = NULL WHERE id = {id}").on('id -> bookId).executeUpdate()
		}
	}
	
	//Kolla vilka böcker som är lånade av "mig". Skickar tillbaka en lista över böcker till BookControllern som sedan skickar en lista av böcker till GUI?
	def getAllMyBooks(userId: Int) {
		DB.withConnection { implicit c =>
			//SQL("SELECT "+this.bookColumns+" FROM books WHERE borrowed_by = {id}").on('id -> userId).as(bookParser *)
		}
	}

}