INSERT INTO tb_user (email, name, password) VALUES ('alex@gmail.com', 'Alex', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (email, name, password) VALUES ('maria@gmail.com', 'Maria', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (email, name, password) VALUES ('bob@gmail.com', 'Bob', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
--SELECT setval('tb_user_id_seq', 3);

INSERT INTO tb_role (authority) VALUES ( 'ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
--SELECT setval('tb_role_id_seq', 2);

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);

INSERT INTO tb_follow (user_follower_id, user_following_id, date) VALUES (1, 2, TIMESTAMP WITHOUT TIME ZONE '2024-01-01T10:00:00');
INSERT INTO tb_follow (user_follower_id, user_following_id, date) VALUES (2, 1, TIMESTAMP WITHOUT TIME ZONE '2024-01-01T15:30:00');
INSERT INTO tb_follow (user_follower_id, user_following_id, date) VALUES (3, 2, TIMESTAMP WITHOUT TIME ZONE '2024-01-01T17:00:00');
INSERT INTO tb_follow (user_follower_id, user_following_id, date) VALUES (3, 1, TIMESTAMP WITHOUT TIME ZONE '2024-01-01T16:00:00');
INSERT INTO tb_follow (user_follower_id, user_following_id, date) VALUES (1, 3, TIMESTAMP WITHOUT TIME ZONE '2024-01-01T16:10:00');

