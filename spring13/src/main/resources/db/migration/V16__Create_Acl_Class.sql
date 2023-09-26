CREATE TABLE IF NOT EXISTS acl_class (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    class varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_2 (class)
);