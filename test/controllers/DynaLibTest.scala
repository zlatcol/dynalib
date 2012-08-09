package controllers2

import org.specs2.mutable._
import org.specs2.mock.Mockito
import play.api.test._
import play.api.test.Helpers._
import controllers.DBHandler

class DynaLibTest extends Specification with Mockito{
	
	"respond to the index Action" in {
		val result = controllers.DynaLib.index()(FakeRequest())
			status(result) must equalTo(OK)
			contentType(result) must beSome("text/html")
			charset(result) must beSome("utf-8")
			contentAsString(result) must contain("Welcome to DynaLib!")
		}
	
	"respond to the addBook Action" in {
		val result = controllers.DynaLib.addBook()(FakeRequest())
			status(result) must equalTo(OK)
			contentType(result) must beSome("text/html")
			charset(result) must beSome("utf-8")
			contentAsString(result) must contain("Add book!")
			contentAsString(result) must contain("title")
			contentAsString(result) must contain("pages")
		}
	
	"respond to the library Action" in {
		running(FakeApplication()) {
			val Some(result) = routeAndCall(FakeRequest(GET, "/library"))
			contentType(result) must beSome("text/html")
			charset(result) must beSome("utf-8")
			contentAsString(result) must contain("Flickan som lekte med en sten")
			}
	}
	
}