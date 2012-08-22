package models
import java.util.Date
import controllers.AuthorController
import controllers.CategoryController
import play.api.libs.json._

case class Book (
	val id: Int,
	var title: String,
	var language: String,
	var pages: Int,
	var borrowedBy: Option[User],
	var borrowedAt: Option[Date],
	var backBy: Option[Date]
) {
	def this(id: Int, title: String, language: String, pages: Int) = {
		this(id, title, language, pages, Option.empty, Option.empty, Option.empty)
	}
	
	lazy val authors = AuthorController.getByBookId(this.id)

	lazy val categories = CategoryController.getByBookId(this.id)
}