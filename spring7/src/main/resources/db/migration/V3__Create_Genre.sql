DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE VARCHAR(255)
);