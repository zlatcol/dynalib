# DynaLib Database

# --- !Ups

CREATE SEQUENCE review_id_seq;
CREATE TABLE reviews (
    id              INT NOT NULL DEFAULT nextval('review_id_seq'),
    bookId          INT NOT NULL,
    userId          INT NOT NULL,
    score        	INT NOT NULL,
    comment     	VARCHAR(500) DEFAULT NULL,
    PRIMARY KEY (id)
);
ALTER SEQUENCE review_id_seq OWNED BY reviews.id;
CREATE INDEX reviewed_by_idx ON reviews (userId);
CREATE INDEX book_reviewd_idx ON reviews (bookId);

# --- !Downs

DROP TABLE review;