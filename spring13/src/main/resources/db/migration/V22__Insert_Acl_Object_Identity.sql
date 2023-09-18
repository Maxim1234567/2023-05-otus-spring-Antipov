--Genre
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1,  1, 100,  NULL, 3, 0),
(2,  1, 200,  NULL, 3, 0),
(3,  1, 300,  NULL, 3, 0),
(4,  1, 400,  NULL, 3, 0),
(5,  1, 500,  NULL, 3, 0),
(6,  1, 600,  NULL, 3, 0),
(7,  1, 700,  NULL, 3, 0),
(8,  1, 800,  NULL, 3, 0),
(9,  1, 900,  NULL, 3, 0),
(10, 1, 1000, NULL, 3, 0),
(11, 1, 1100, NULL, 3, 0);

--Author
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(12, 2, 100, NULL, 3, 0),
(13, 2, 200, NULL, 3, 0),
(14, 2, 300, NULL, 3, 0),
(15, 2, 400, NULL, 3, 0);

--Book
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(16, 3, 100, NULL, 3, 0),
(17, 3, 200, NULL, 3, 0),
(18, 3, 300, NULL, 3, 0),
(19, 3, 400, NULL, 3, 0);