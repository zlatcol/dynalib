package models

import org.specs2.mutable._
import org.specs2.mock.Mockito
import play.api.test._
import play.api.test.Helpers._

class AuthorHelperTest extends Specification with Mockito {
	
	"AuthorHelper should return correct success msg" in {
		val msg = AuthorHelper.getAddedMsg("name")
		msg must equalTo("Author: name added")
	}
}