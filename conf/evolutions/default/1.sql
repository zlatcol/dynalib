# DynaLib Database

# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE users (
    id              INT NOT NULL DEFAULT nextval('user_id_seq'),
    email           VARCHAR(128) NOT NULL,
    name            VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);
ALTER SEQUENCE user_id_seq OWNED BY users.id;
CREATE UNIQUE INDEX email_idx ON users (email);

CREATE SEQUENCE book_id_seq;
CREATE TABLE books (
    id              INT NOT NULL DEFAULT nextval('book_id_seq'),
    title           VARCHAR(255) NOT NULL,
    pages           INT NOT NULL DEFAULT 0,
    language        VARCHAR(50) NOT NULL DEFAULT 'English',
    borrowed_by     INT DEFAULT NULL,
    date_borrowed   date DEFAULT NULL,
    date_back       date DEFAULT NULL,
    PRIMARY KEY (id)
);
ALTER SEQUENCE book_id_seq OWNED BY books.id;
CREATE INDEX borrowed_by_idx ON books (borrowed_by);

CREATE SEQUENCE author_id_seq;
CREATE TABLE authors (
    id              INT NOT NULL DEFAULT nextval('author_id_seq'),
    name            VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
ALTER SEQUENCE author_id_seq OWNED BY authors.id;

CREATE SEQUENCE category_id_seq;
CREATE TABLE category (
    id              INT NOT NULL DEFAULT nextval('category_id_seq'),
    name            VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
ALTER SEQUENCE category_id_seq OWNED BY category.id;

CREATE TABLE book_author (
    bookId          INT NOT NULL,
    authorId        INT NOT NULL,
    PRIMARY KEY (bookId, authorId)
);

CREATE TABLE book_category (
    bookId          INT NOT NULL,
    categoryId      INT NOT NULL,
    PRIMARY KEY (bookId, categoryId)
);


# --- !Downs

DROP TABLE books;
DROP TABLE authors;
DROP TABLE users;
DROP TABLE category;
DROP TABLE book_author;
DROP TABLE book_category;