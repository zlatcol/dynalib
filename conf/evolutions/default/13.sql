# PlayApp Database

# --- !Ups

ALTER TABLE books CHANGE COLUMN borrowed_by borrowed_by int(5) default NULL;
UPDATE books SET borrowed_by = 1, date_back = '2012-09-01' WHERE id = 2;

# --- !Downs

ALTER TABLE books DROP COLUMN borrowed_by;