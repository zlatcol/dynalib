# PlayApp Database

# --- !Ups

CREATE TABLE books (id SERIAL NOT NULL, pages integer NOT NULL DEFAULT 0, language VARCHAR(50) NOT NULL DEFAULT 'English' ,title VARCHAR(255) NOT NULL DEFAULT '', borrowed_by varchar(50) default NULL, date_back DATE default NULL, PRIMARY KEY (id));
CREATE TABLE book_category (bookId integer NOT NULL, categoryId integer NOT NULL);
CREATE INDEX book_category_idx ON book_category (bookId,categoryId);
CREATE TABLE authors (id SERIAL NOT NULL, name VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));
CREATE TABLE book_author (bookId integer NOT NULL, authorId integer NOT NULL);
CREATE INDEX book_author_idx ON book_author (bookId,authorId);
CREATE INDEX author_book_idx ON book_author (authorId, bookId);
CREATE INDEX author_book_idx ON book_category (categoryId, bookId);
#CREATE TABLE category (id serial NOT NULL, name VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));
CREATE TABLE users (id serial NOT NULL, name varchar(50) NOT NULL, PRIMARY KEY (id));

# --- !Downs

DROP TABLE books;
DROP TABLE book_category;
DROP TABLE authors;
DROP INDEX IF EXISTS book_category_idx;
DROP INDEX IF EXISTS category_book_idx;
#DROP TABLE category;
DROP TABLE book_author;
DROP INDEX IF EXISTS book_author_idx;
DROP INDEX IF EXISTS author_book_idx;
DROP TABLE users;
