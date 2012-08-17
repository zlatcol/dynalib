# PlayApp Database

# --- !Ups

CREATE TABLE authors (id INT(5) NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));

# --- !Downs

DROP TABLE authors;