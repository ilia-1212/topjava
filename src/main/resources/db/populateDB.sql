DELETE FROM USER_ROLE;
DELETE FROM MEAL;
DELETE FROM USERS;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO MEAL (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 100000),
       ('2020-01-30 13:00:00', 'Обед', 1000, 100000),
       ('2020-01-30 20:00:00', 'Ужин', 500, 100000),
       ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 100000),
       ('2020-01-31 10:00:00', 'Завтрак', 500, 100000),
       ('2020-01-31 13:00:00', 'Обед', 1000, 100000),
       ('2020-01-31 20:00:00', 'Ужин', 510, 100000),
       ('2020-01-31 14:00:00', 'Админ ланч', 510, 100001),
       ('2020-01-31 21:00:00', 'Админ ужин', 1500, 100001);
