# PlayApp Database

# --- !Ups

ALTER TABLE books ADD COLUMN borrowed_by varchar(50) default "", ADD COLUMN date_back DATE default "0000-00-00";

# --- !Downs

ALTER TABLE books DROP COLUMN borrowed_by;
ALTER TABLE books DROP COLUMN date_back;

