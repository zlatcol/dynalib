# PlayApp Database

# --- !Ups

ALTER TABLE books CHANGE COLUMN date_back date_back DATE default NULL;

# --- !Downs

ALTER TABLE books DROP COLUMN date_back;