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

CREATE TABLE readers
(
    id                  integer NOT NULL,
    firstName           varchar(50) NOT NULL,
    lastName            varchar(50) NOT NULL,
    CONSTRAINT PK_reader PRIMARY KEY ( id )
);

CREATE TABLE distribution
(
    distribution_id integer NOT NULL,
    reader_id       integer NOT NULL,
    book_id         bigint NOT NULL,
    date_give       date NOT NULL,
    date_return     date,
    CONSTRAINT PK_distribution PRIMARY KEY ( distribution_id ),
    CONSTRAINT FK_reader FOREIGN KEY ( reader_id ) REFERENCES readers ( id ),
    CONSTRAINT FK_edition FOREIGN KEY ( book_id ) REFERENCES books ( id )
);