# PlayApp Database

# --- !Ups

#Books
INSERT INTO books (title, pages, language) VALUES ('Flickan som lekte med en sten',300,'English'),('Stenen som lekte med en bil',334,'English'),('Bilen som var scrum master inom ett agilt projekt',1334,'English'),('Agila projektet som mest av allt ville vara en sten', 678,'English'),('Stenen som lekte med grus',245,'English'),('The girl who played with a rock',1368,'English'),('Berg, en illustrerad guide till hur man undviker dem',767,'Swedish'),('Who to write a book as a single author',425,'English'),('How to write a book as a team of authors',722,'English');
#Authors
INSERT INTO authors (name) VALUES ('Jan Guillou'),('Stephen King'),('George RR Martin'),('Albert Einstein'),('Jean-Paul Sartre'),('Henrik Kniberg'),('Paulo Coelho');
#Book -> Authors
INSERT INTO book_author (bookId, authorId) VALUES (1,1),(2,2),(3,3),(4,4),(4,5),(4,6),(5,7),(6,7),(7,7),(8,1),(8,2),(8,3),(8,4),(8,5),(8,6),(8,7),(9,1);
#Users
INSERT INTO users (name) VALUES ('Jakob Skwarski'),('John Horsetwigg');

# --- !Downs

DELETE FROM books;
DELETE FROM authors;
DELETE FROM book_author;
DELETE FROM users;