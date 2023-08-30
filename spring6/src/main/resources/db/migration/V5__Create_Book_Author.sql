DROP TABLE IF EXISTS BOOK_AUTHOR;
CREATE TABLE BOOK_AUTHOR
(
    BOOK_ID BIGINT,
    AUTHOR_ID BIGINT,
    CONSTRAINT FK_BOOK_AUTHOR_BOOK_ID FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID),
    CONSTRAINT FK_BOOK_AUTHOR_AUTHOR_ID FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (ID),
    PRIMARY KEY (BOOK_ID, AUTHOR_ID)
);