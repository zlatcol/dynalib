package controllers

import play.api._
import play.api.mvc._
import models.User
import play.api.data._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object UserController {
	
	val userParser = 
  		get[Int]("id")~
  		get[String]("name") map {
  			case id~name => new User(id, name)
  		}
	
	def getNumberOfUsers(): Int = {
		val result = DB.withConnection ( implicit c =>
			SQL("SELECT count(id) AS count FROM users").apply().head
		)
		Integer.parseInt(result[Long]("count").toString())
	}
	
	def getUsers: List[User] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, name FROM users").as(userParser *)
		}
	}
	
	def getUserById(id: Int): Option[User] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, name FROM users WHERE id = {id}").on('id -> id).as(userParser.singleOpt)
		}
	}

}