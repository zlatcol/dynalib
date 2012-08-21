package controllers
import play.api.mvc._
import traits.Secured
import play.api.libs.json.Json
import models.Book

object ApiController extends Controller with Secured {
	def books() = withUser {
		implicit user => Action {
			Ok(Json.toJson(BookController.getAllBooks))
		}
	}
}