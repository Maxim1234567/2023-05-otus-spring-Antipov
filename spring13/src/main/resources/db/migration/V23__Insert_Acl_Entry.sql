--------------------------ADMIN
--Genre
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1,  1,  1, 1, 1, 1, 1, 1),
(2,  2,  1, 1, 1, 1, 1, 1),
(3,  3,  1, 1, 1, 1, 1, 1),
(4,  4,  1, 1, 1, 1, 1, 1),
(5,  5,  1, 1, 1, 1, 1, 1),
(6,  6,  1, 1, 1, 1, 1, 1),
(7,  7,  1, 1, 1, 1, 1, 1),
(8,  8,  1, 1, 1, 1, 1, 1),
(9,  9,  1, 1, 1, 1, 1, 1),
(10, 10, 1, 1, 1, 1, 1, 1),
(11, 11, 1, 1, 1, 1, 1, 1);

--Author
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(12, 12, 1, 1, 1, 1, 1, 1),
(13, 13, 1, 1, 1, 1, 1, 1),
(14, 14, 1, 1, 1, 1, 1, 1),
(15, 15, 1, 1, 1, 1, 1, 1);

--Book
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(16, 16, 1, 1, 1, 1, 1, 1),
(17, 17, 1, 1, 1, 1, 1, 1),
(18, 18, 1, 1, 1, 1, 1, 1),
(19, 19, 1, 1, 1, 1, 1, 1);

--------------------------USER
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(20, 16, 2, 2, 1, 1, 1, 1),
(21, 17, 2, 2, 1, 1, 1, 1),
(22, 18, 2, 2, 1, 1, 1, 1),
(23, 19, 2, 2, 1, 1, 1, 1);