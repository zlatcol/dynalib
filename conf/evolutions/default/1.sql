# playapp schema

# --- !Ups

CREATE TABLE results (id int(11) DEFAULT NULL AUTO_INCREMENT, result varchar(255) NOT NULL DEFAULT '0',KEY idx_id (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs

DROP TABLE results;
