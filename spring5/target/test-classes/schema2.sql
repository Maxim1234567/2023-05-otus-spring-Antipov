DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR
(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    AGE INTEGER,
    YEAR_BIRTHDATE INTEGER
);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    YEAR_ISSUE INTEGER,
    NUMBER_PAGES INTEGER
);

DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE VARCHAR(255)
);

DROP TABLE IF EXISTS BOOK_GENRE;
CREATE TABLE BOOK_GENRE
(
    BOOK_ID BIGINT,
    GENRE_ID BIGINT,
    CONSTRAINT FK_BOOK_GENRE_BOOK_ID FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID),
    CONSTRAINT FK_BOOK_GENRE_GENRE_ID FOREIGN KEY (GENRE_ID) REFERENCES GENRE (ID),
    PRIMARY KEY (BOOK_ID, GENRE_ID)
);

DROP TABLE IF EXISTS BOOK_AUTHOR;
CREATE TABLE BOOK_AUTHOR
(
    BOOK_ID BIGINT,
    AUTHOR_ID BIGINT,
    CONSTRAINT FK_BOOK_AUTHOR_BOOK_ID FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID),
    CONSTRAINT FK_BOOK_AUTHOR_AUTHOR_ID FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (ID),
    PRIMARY KEY (BOOK_ID, AUTHOR_ID)
);