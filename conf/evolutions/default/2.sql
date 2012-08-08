# playapp schema

# --- !Ups

ALTER TABLE results ADD COLUMN operation varchar(255) not null default 'error';

# --- !Downs

ALTER TABLE results DROP COLUMN operation;