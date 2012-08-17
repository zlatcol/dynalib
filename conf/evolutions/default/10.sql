# PlayApp Database

# --- !Ups

CREATE TABLE book_category (bookId integer NOT NULL, categoryId integer NOT NULL , KEY book_category_idx (bookId, categoryId), KEY category_book_idx (categoryId, bookId));

# --- !Downs

DROP TABLE book_category;