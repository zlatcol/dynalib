package controllers
import play.api.mvc._
import traits.Secured
import play.api.libs.json.Json
import models.Author
import play.api.data.Form
import play.api.data._
import play.api.data.Forms._


object ApiController extends Controller with Secured {
  def addAuthor = withUser {
    implicit user => Action { implicit request =>
      Form("name" -> nonEmptyText).bindFromRequest.fold(
        errors => badRequest,
        name => {
          val filteredName = name.split(" ").map(_.trim.toLowerCase.capitalize).filter(s => s.length > 0) mkString " "
          val author = new Author(0, filteredName)
          val authorId = AuthorController.addAuthor(author)
          if (authorId > 0) {
            Ok(Json.toJson(Map("id" -> authorId)))
          } else {
            badRequest
          }
        }
      )
    }
  }

  val badRequest = BadRequest(Json.toJson(Map("error" -> "Fail")));
}