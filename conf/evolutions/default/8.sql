# PlayApp Database

# --- !Ups

CREATE TABLE users (id serial NOT NULL, name varchar(50) NOT NULL, PRIMARY KEY (id));

# --- !Downs

DROP TABLE users;