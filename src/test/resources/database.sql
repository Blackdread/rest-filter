CREATE TABLE parent_entity
(
    id          BIGINT                  NOT NULL PRIMARY KEY,
    name        VARCHAR(50)             NOT NULL,
--     create_time DATETIME       NOT NULL,
    create_time TIMESTAMP WITH TIMEZONE NOT NULL,
    total       DECIMAL(20, 5)          NOT NULL,
    count       INT                     NOT NULL,
    local_date  DATE                    NOT NULL,
    a_short     SMALLINT                NOT NULL,
    active      BOOLEAN                 NOT NULL,
    uuid        UUID                    NOT NULL,
    duration    VARCHAR(50)             NOT NULL
);

/*
TODO see "INTERVAL DAY" to do duration
TODO add more type like time, etc
*/

CREATE TABLE child_entity
(
    id          BIGINT                  NOT NULL PRIMARY KEY,
    name        VARCHAR(50)             NOT NULL,
--     create_time DATETIME       NOT NULL,
    create_time TIMESTAMP WITH TIMEZONE NOT NULL,
    total       DECIMAL(20, 5)          NOT NULL,
    count       INT                     NOT NULL,
    local_date  DATE                    NOT NULL,
    a_short     SMALLINT                NOT NULL,
    active      BOOLEAN                 NOT NULL,
    uuid        UUID                    NOT NULL,
    duration    VARCHAR(50)             NOT NULL,
    parent_id   NUMBER(11)              NULL
);

/*
 Below is just a more complex example, not used for now

CREATE TABLE language
(
    id          NUMBER(7) NOT NULL PRIMARY KEY,
    cd          CHAR(2)   NOT NULL,
    description VARCHAR2(50)
);

CREATE TABLE author
(
    id            NUMBER(7)    NOT NULL PRIMARY KEY,
    first_name    VARCHAR2(50),
    last_name     VARCHAR2(50) NOT NULL,
    date_of_birth DATE,
    year_of_birth NUMBER(7),
    distinguished NUMBER(1)
);

CREATE TABLE book
(
    id           NUMBER(7)     NOT NULL PRIMARY KEY,
    author_id    NUMBER(7)     NOT NULL,
    title        VARCHAR2(400) NOT NULL,
    published_in NUMBER(7)     NOT NULL,
    language_id  NUMBER(7)     NOT NULL,

    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_book_language FOREIGN KEY (language_id) REFERENCES language (id)
);

CREATE TABLE book_store
(
    name VARCHAR2(400) NOT NULL UNIQUE
);

CREATE TABLE book_to_book_store
(
    name    VARCHAR2(400) NOT NULL,
    book_id INTEGER       NOT NULL,
    stock   INTEGER,

    PRIMARY KEY (name, book_id),
    CONSTRAINT fk_b2bs_book_store FOREIGN KEY (name) REFERENCES book_store (name) ON DELETE CASCADE,
    CONSTRAINT fk_b2bs_book FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE
);
//*/
