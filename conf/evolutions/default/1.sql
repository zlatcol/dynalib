# PlayApp Database

# --- !Ups

CREATE TABLE books (id SERIAL NOT NULL, title VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));

# --- !Downs

DROP TABLE books;