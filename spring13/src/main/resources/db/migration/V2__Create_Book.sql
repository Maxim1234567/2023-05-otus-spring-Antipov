DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    YEAR_ISSUE INTEGER,
    NUMBER_PAGES INTEGER
);