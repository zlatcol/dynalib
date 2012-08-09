# PlayApp database

# ---!Ups

ALTER TABLE books ADD COLUMN language varchar(50) NOT NULL DEFAULT 'English';

# ---!Downs

ALTER TABLE books DROP COLUMN language;