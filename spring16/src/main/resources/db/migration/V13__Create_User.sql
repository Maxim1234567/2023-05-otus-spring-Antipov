DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
                       ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                       USERNAME VARCHAR(255),
                       PASSWORD VARCHAR(255),
                       ROLE VARCHAR(255)
);