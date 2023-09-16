CREATE TABLE IF NOT EXISTS acl_object_identity (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    object_id_class bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object bigint(20) DEFAULT NULL,
    owner_sid bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);