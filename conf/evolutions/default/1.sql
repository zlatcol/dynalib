# PlayApp Database

# --- !Ups

CREATE TABLE books (id INT(5) NOT NULL AUTO_INCREMENT, title VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));

# --- !Downs

DROP TABLE books;