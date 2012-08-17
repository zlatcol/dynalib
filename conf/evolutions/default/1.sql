# PlayApp Database

# --- !Ups

CREATE TABLE books (id SERIAL NOT NULL, title VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));
CREATE TABLE book_category (bookId integer NOT NULL, categoryId integer NOT NULL);
CREATE INDEX book_category_idx ON book_category (bookId,categoryId);
CREATE INDEX author_book_idx ON book_category (categoryId, bookId);
CREATE TABLE category (id serial NOT NULL, name VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));

# --- !Downs

DROP TABLE books;
DROP TABLE book_category;
DROP INDEX IF EXISTS book_category_idx;
DROP INDEX IF EXISTS category_book_idx;
DROP TABLE category;
