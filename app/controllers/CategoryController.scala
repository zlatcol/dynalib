package controllers

import play.api._
import play.api.mvc._
import models.Category
import play.api.data._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object CategoryController {
	
	val categoryParser = 
  		get[Int]("id")~
  		get[String]("name") map {
  			case id~name => new Category(id, name)
  		}
	
	val countParser = get[Int]("count") map {case count => count}
	
	def getCategoryByBookId(id: Int): List[Category] = {
		getByBookId(id)
	}
	
	def getCategories: List[Category] = {
		DB.withConnection { implicit c =>
			SQL("SELECT id, name FROM category").as(categoryParser *)
		}
	}
	
	def getByBookId(id: Int): List[Category] = {
		DB.withConnection { implicit c =>
			SQL("SELECT category.id, category.name FROM category WHERE id IN (SELECT categoryId FROM book_category WHERE bookId = {id})").on('id -> id).as(categoryParser *)
		}
	}
	
}