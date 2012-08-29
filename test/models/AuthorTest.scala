package models

import org.specs2.mutable._
import org.specs2.mock.Mockito
import play.api.test._
import play.api.test.Helpers._
import models.Author

class AuthorTest  extends Specification {
	"should be able to read/write fields from author" in {
		val a = new Author(1, "Name")
		a.id must equalTo(1)
		a.name must equalTo("Name")	
	}
}