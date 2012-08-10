# PlayApp Database

# --- !Ups

CREATE TABLE book_author (bookId INT(5) NOT NULL, authorId INT(5) NOT NULL , KEY book_author_idx (bookId, authorId), KEY author_book_idx (authorId, bookId)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs

DROP TABLE book_author;