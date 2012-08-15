# PlayApp Database

# --- !Ups

CREATE TABLE book_category (bookId INT(5) NOT NULL, categoryId INT(5) NOT NULL , KEY book_category_idx (bookId, categoryId), KEY category_book_idx (categoryId, bookId)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs

DROP TABLE book_category;