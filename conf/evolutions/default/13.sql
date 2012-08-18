# PlayApp Database

# --- !Ups

ALTER TABLE books CHANGE COLUMN borrowed_by borrowed_by int(5) default NULL;

# --- !Downs

ALTER TABLE books DROP COLUMN borrowed_by;