package controllers

import org.specs2.mutable._
import org.specs2.mock.Mockito
import play.api.test._
import play.api.test.Helpers._
import models.Book

class BookTest extends Specification with Mockito {
  
	"should be able to read/write fields from book" in {
		val b = new Book(1, "Title", "English", 200)
		b.id must equalTo(1)
		b.title must equalTo("Title")
		b.language must equalTo("English")
		b.pages must equalTo(200)		
	}

	"return a single book" in {
	  running(FakeApplication()) {
		val book = BookController.getBookById(1).get
			assert(book.isInstanceOf[Book])
			book.id must equalTo(1)
		}
	}

	"should not find book" in {
	  running(FakeApplication()) {
		val book = BookController.getBookById(-1).getOrElse("not found")
			book must equalTo("not found")
		}
	}
	
	"find list of books" in {
  	  running(FakeApplication()) {
  	    val books = BookController.getAllBooks
  	    assert(books(0).isInstanceOf[Book])
  	  }
	}
	
	"should give error msg when adding book with missing info" in {
		running(FakeApplication()) {
		val result = controllers.DynaLib.handleAddBookRequest()(FakeRequest().withFormUrlEncodedBody(("author","1")))
			status(result) must equalTo(400) //Bad request, finns det någon motsvarighet till OK för detta? Jag kan inte hitta nån.
			contentType(result) must beSome("text/html")
			charset(result) must beSome("utf-8")
			contentAsString(result) must contain("Something went wrong")
		}
	}
}