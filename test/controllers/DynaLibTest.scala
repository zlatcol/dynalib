package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._


class DynaLibTest extends Specification {
	
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
	
}