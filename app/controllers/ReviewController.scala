package controllers

import play.api._
import play.api.mvc._
import models.Review
import models.Book
import models.User
import play.api.data._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object ReviewController {
	
	val reviewParser = 
		get[Int]("id")~
		get[Int]("bookId")~
		get[Int]("userId")~
		get[Int]("score")~
		get[String]("comment") map {
			case id~bookId~userId~score~comment => new Review(id, bookId, userId, score, comment) 
		}
	
	def getReviewsByBookId(bookId: Int): List[Review] = {
		DB.withConnection { implicit c => 
			SQL("SELECT id, bookId, userId, score, comment FROM reviews WHERE bookId = {bookId}").on('bookId -> bookId).as(reviewParser *)
		}
	}
	
	def addReview(review: Review) {
		DB.withConnection { implicit c =>
			SQL("INSERT INTO reviews (bookId, userId, score, comment) VALUES ({bId}, {uId}, {score}, {comment})").on('bId -> review.bookId, 'uId -> review.userId, 'score -> review.score, 'comment -> review.comment).executeInsert()	
		}
	}
	
	def scoreMap(score: Int): String = {
		var grade = score match {
			case -1 => "does not recommend"
			case 0 => "has no strong opinions what so ever about"
			case 1 => "recommends"
		}
		grade
	}
	
	def getNameOfReviewer(userId: Int): String = {
		val result = DB.withConnection { implicit c =>
			SQL("SELECT name FROM users WHERE id = {userId}").on('userId -> userId).apply().head
		}
		result.asList.head.toString()
	}

}