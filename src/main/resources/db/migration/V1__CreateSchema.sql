CREATE TABLE authors
(
    id bigint NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    CONSTRAINT PK_authors PRIMARY KEY (id)
);

CREATE TABLE library
(
    library_id bigint NOT NULL,
    address    varchar(50) NOT NULL,
    CONSTRAINT PK_library PRIMARY KEY ( library_id )
);

CREATE TABLE storage
(
    storage_id  bigint NOT NULL,
    library_id  bigint NOT NULL,
    room_number bigint NOT NULL,
    CONSTRAINT PK_storage PRIMARY KEY ( storage_id ),
    CONSTRAINT FK_storage_library FOREIGN KEY ( library_id ) REFERENCES library ( library_id )
);

CREATE TABLE bookPosition
(
    position_id  bigint NOT NULL,
    storage_id   bigint NOT NULL,
    rack_number  bigint NOT NULL,
    shelf_number bigint NOT NULL,
    CONSTRAINT PK_bookPosition PRIMARY KEY ( position_id ),
    CONSTRAINT FK_bookPosition_storage FOREIGN KEY ( storage_id ) REFERENCES storage ( storage_id )
);

CREATE TABLE books
(
    id bigint NOT NULL,
    title varchar(50) NOT NULL,
    author_id  bigint NOT NULL,
    position_id bigint NOT NULL,
    CONSTRAINT PK_books PRIMARY KEY (id),
    CONSTRAINT FK_books_authors FOREIGN KEY (author_id) REFERENCES authors ( id ),
    CONSTRAINT FK_books_bookPosition FOREIGN KEY (position_id) REFERENCES bookPosition ( position_id )
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
    id integer NOT NULL,
    reader_id       integer NOT NULL,
    book_id         bigint NOT NULL,
    date_give       date NOT NULL,
    date_return     date,
    CONSTRAINT PK_distribution PRIMARY KEY ( id ),
    CONSTRAINT FK_reader FOREIGN KEY ( reader_id ) REFERENCES readers ( id ),
    CONSTRAINT FK_edition FOREIGN KEY ( book_id ) REFERENCES books ( id )
);