package models

import org.specs2.mutable._
import org.specs2.mock.Mockito
import play.api.test._
import play.api.test.Helpers._
import models.BookHelper

class BookHelperTest extends Specification {
	"BookHelper should return correct success msg" in {
		val msg = BookHelper.getAddedMsg("title")
		msg must equalTo("Book: title added")
	}
}