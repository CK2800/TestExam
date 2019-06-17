USE seed;
INSERT INTO users (user_name, user_pass) VALUES ('user', '$2a$10$nYqwmiS8Mf.Y3DXEP36oVOpTf0Bv2IXVPwj8ah5CS7RRYAisCWexS');
INSERT INTO users (user_name, user_pass) VALUES ('admin', '$2a$10$dAJcVYfNde9xVUExnZvjm./eksrvSKwjE0yEtGCOxwdVweJ4yqVvG');
INSERT INTO roles (role_name) VALUES ('user');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('user', 'user');
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'user');
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'admin');