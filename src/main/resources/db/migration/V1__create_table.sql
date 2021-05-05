CREATE TABLE book
(
    book_id              bigint NOT NULL,
    title                varchar(50) NOT NULL,
    author_id            bigint NOT NULL,
    CONSTRAINT PK_book PRIMARY KEY ( book_id )
);