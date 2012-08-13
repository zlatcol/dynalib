package models
import java.util.Date

case class Book (
	val id: Int,
	var title: String,
	var language: String,
	var pages: Int,
	var borrowedBy: Option[String],
	var borrowedAt: Option[Date]
) {
  	def this(id: Int, title: String, language: String, pages: Int) = {
		this(id, title, language, pages, Option.empty, Option.empty)
	}
}