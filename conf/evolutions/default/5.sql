# PlayApp database

# ---!Ups

ALTER TABLE books ADD COLUMN pages INT NOT NULL DEFAULT '0';

# ---!Downs

ALTER TABLE books DROP COLUMN pages;