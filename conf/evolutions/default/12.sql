# PlayApp Database

# --- !Ups

DELETE FROM users;
ALTER TABLE users ADD COLUMN email varchar(64) default NULL AFTER id;
CREATE UNIQUE INDEX email_idx ON users(email);
INSERT INTO users (id, email, name) VALUES (1, 'test.user@dynabyte.se','Test User');


# --- !Downs

ALTER TABLE users DROP COLUMN email;
DELETE from users;