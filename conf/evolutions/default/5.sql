# DynaLib Database

# --- !Ups

ALTER TABLE books DROP COLUMN borrowed_by;
ALTER TABLE books DROP COLUMN date_borrowed;
ALTER TABLE books DROP COLUMN date_back;

# --- !Downs

ALTER TABLE books ADD COLUMN borrowed_by INT DEFAULT NULL;
ALTER TABLE books ADD COLUMN date_borrowed date DEFAULT NULL;
ALTER TABLE books ADD COLUMN date_back date DEFAULT NULL;
CREATE INDEX borrowed_by_idx ON books (borrowed_by);
