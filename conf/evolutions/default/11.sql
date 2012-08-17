# PlayApp Database

# --- !Ups

CREATE TABLE category (id INT(5) NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL DEFAULT '', PRIMARY KEY (id));
INSERT INTO category (name) VALUES ('Frontend'),('Backend'),('Java'),('CSS'),('JavaScript'),('Scala'),('Scrum'),('Agile'),('TIBCO');

# --- !Downs

DROP TABLE category;