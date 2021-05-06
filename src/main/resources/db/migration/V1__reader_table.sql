CREATE TABLE reader
(
    reader_id           integer NOT NULL,
    firstName           varchar(50) NOT NULL,
    lastName            varchar(50) NOT NULL,
    CONSTRAINT PK_reader PRIMARY KEY ( reader_id )
);

CREATE SEQUENCE reader_seq INCREMENT 50 START 1 MINVALUE 1;