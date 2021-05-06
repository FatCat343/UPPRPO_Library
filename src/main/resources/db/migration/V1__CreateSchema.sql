CREATE TABLE authors (
    id bigint NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    CONSTRAINT PK_authors PRIMARY KEY (id)
);

CREATE TABLE books
(
    id bigint NOT NULL,
    title varchar(50) NOT NULL,
    author_id  bigint NOT NULL,
    CONSTRAINT PK_books PRIMARY KEY (id),
    CONSTRAINT FK_books_authors FOREIGN KEY (author_id) REFERENCES authors
);