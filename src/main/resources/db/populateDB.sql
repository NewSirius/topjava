DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2018-03-21 07:00:00', 'завтрак', 500, 100000),
  ('2018-03-21 12:00:00', 'обед', 700, 100000),
  ('2018-03-21 19:00:00', 'ужин', 1000, 100000),
  ('2018-03-22 07:00:00', 'завтрак', 300, 100000),
  ('2018-03-22 12:00:00', 'обед', 700, 100000),
  ('2018-03-22 16:00:00', 'полдник', 400, 100000),
  ('2018-03-22 19:00:00', 'ужин', 900, 100000),
  ('2018-03-31 12:00:00', 'ужин админ', 700, 100001);
