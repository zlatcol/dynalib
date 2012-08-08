package controllers

import play.api._

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

object Application extends Controller {

	val form = Form(
		tuple(
			"x" -> number,
			"y" -> number,
			"options" -> text
		)
	)

	def index = Action {
		Ok(views.html.index(form))
	}

	def operationHandler() = Action { implicit request =>
		form.bindFromRequest.fold(
			errors => BadRequest(views.html.index(errors)),
			result => {
				val addition = result._1 + result._2
				val opResult = Operations.doOperation(result._1, result._2, result._3)
				Ok(views.html.result(opResult))
			}
		)

	}
	
	def all = Action {
		Ok(views.html.results(DBHandler.getAllResults()))
	}
	
}