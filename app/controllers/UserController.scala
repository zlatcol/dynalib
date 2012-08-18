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
  		get[String]("email")~
  		get[String]("name") map {
  			case id~email~name => new User(id, email, name)
  		}
	
	def getNumberOfUsers(): Int = {
		val result = DB.withConnection ( implicit c =>
			SQL("SELECT count(id) AS count FROM users").apply().head
		)
		Integer.parseInt(result[Long]("count").toString())
	}
	
	def getUsers: List[User] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, email, name FROM users").as(userParser *)
		}
	}
	
	def getUserById(id: Int): Option[User] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, email, name FROM users WHERE id = {id}").on('id -> id).as(userParser.singleOpt)
		}
	}

	def getUserByEmail(email: String): Option[User] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, email, name FROM users WHERE email = {email}").on('email -> email).as(userParser.singleOpt)
		}
	}

	def create(user: User): Int = {
		var id = DB.withConnection { implicit c =>
			SQL("INSERT INTO users (email, name) VALUES ({email}, {name})").on('email -> user.email, 'name -> user.name).executeInsert()
		}.getOrElse(0)
		Integer.parseInt(id.toString())
	}

	def changeName(id: Int, name: String): Int = {
		DB.withConnection { implicit c =>
			SQL("UPDATE users SET name = {name} WHERE id = {id}").on('name -> name, 'id -> id).executeUpdate()
		}
	}

}