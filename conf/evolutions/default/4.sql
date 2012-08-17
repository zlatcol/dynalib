# PlayApp Database

# --- !Ups

CREATE TABLE authors (id SERIAL NOT NULL, name VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));

# --- !Downs

DROP TABLE authors;