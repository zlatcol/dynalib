package models

class Book(title: String, author: Author) {
	
	def getTitle: String = {
		return title
	}
	
	def getAuthor: Author = {
		return author
	}
	
}