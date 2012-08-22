# DynaLib Database

# --- !Ups

CREATE SEQUENCE copy_id_seq;
CREATE TABLE copies (
    id              INT NOT NULL DEFAULT nextval('copy_id_seq'),
    bookId          INT NOT NULL,
    borrowed_by     INT DEFAULT NULL,
    date_borrowed   date DEFAULT NULL,
    date_back       date DEFAULT NULL,
    PRIMARY KEY (id)
);
ALTER SEQUENCE copy_id_seq OWNED BY copies.id;
CREATE INDEX copy_borrowed_by_idx ON copies (borrowed_by);
CREATE INDEX book_idx ON copies (bookId);

# --- !Downs

DROP TABLE copies;