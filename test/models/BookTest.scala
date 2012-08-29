package models

import org.specs2.mutable._
import org.specs2.mock.Mockito
import org.mockito.Mockito.verify
import models.Book
import controllers.BookController
import play.api.test._
import play.api.test.Helpers._
import models.Author


class BookTest extends Specification {
  
	"should be able to read/write fields from book" in {
		val b = new Book(1, "Title", "English", 200)
		b.id must equalTo(1)
		b.title must equalTo("Title")
		b.language must equalTo("English")
		b.pages must equalTo(200)	
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
}