# PlayApp Database

# --- !Ups

CREATE TABLE users (id INT(5) NOT NULL AUTO_INCREMENT, name varchar(50) NOT NULL, PRIMARY KEY (id));

# --- !Downs

DROP TABLE users;