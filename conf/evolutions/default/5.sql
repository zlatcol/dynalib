# PlayApp Database

# --- !Ups

CREATE TABLE book_author (bookId integer NOT NULL, authorId integer NOT NULL);
CREATE INDEX book_author_idx ON book_author (bookId,authorId);
CREATE INDEX author_book_idx ON book_author (authorId, bookId);

# --- !Downs

DROP TABLE book_author;
DROP INDEX IF EXISTS book_author_idx;
DROP INDEX IF EXISTS author_book_idx;