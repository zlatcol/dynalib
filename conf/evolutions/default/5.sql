# PlayApp Database

# --- !Ups

CREATE TABLE book_author (bookId integer NOT NULL, authorId integer NOT NULL , KEY book_author_idx (bookId, authorId), KEY author_book_idx (authorId, bookId));

# --- !Downs

DROP TABLE book_author;