CREATE TABLE users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username     VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    mail    VARCHAR(255) UNIQUE NOT NULL,
    age      TINYINT
);
CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    UNIQUE (user_id, role_id)
);
INSERT INTO users (id, username, password, mail, age)
VALUES (1, 'user', '$2y$12$2NFuzQaWAEIC.Bw40f5u9ejkFIsohxS9iYf3saJOZ4udVGFENXATK', 'test_1@gmail.com', 30),
       (2, 'admin', '$2y$12$ddXGx20Ql4k183.wThy23uEJpTQgcxAnkugPz2UubJxHG1Qk1rpXS', 'test_2@gmail.com', 31);
INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 2),
       (2, 1);