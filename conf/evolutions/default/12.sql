# PlayApp Database

# --- !Ups

DELETE FROM users;
ALTER TABLE users ADD COLUMN email varchar(64) default NULL AFTER id;
CREATE UNIQUE INDEX email_idx ON users(email);
INSERT INTO users (email, name) VALUES ('jakob.skwarski@dynabyte.se','Jakob Skwarski');


# --- !Downs

ALTER TABLE users DROP COLUMN email;