DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS genre;

CREATE TABLE genre (
  id    BIGSERIAL PRIMARY KEY NOT NULL,
  type  VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE author (
  id   BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE book (
  id            BIGSERIAL PRIMARY KEY NOT NULL,
  title         VARCHAR(255) NOT NULL,
  author_id     BIGINT,
  genre_id      BIGINT,
  FOREIGN KEY (author_id) REFERENCES author(id),
  FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE comment (
  id            BIGSERIAL PRIMARY KEY NOT NULL,
  comment       VARCHAR(4000) NOT NULL,
  creation_date TIMESTAMP NOT NULL,
  book_id       BIGINT REFERENCES book(id) ON DELETE CASCADE
);

CREATE TABLE app_user (
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  login             VARCHAR(4000) NOT NULL,
  password          VARCHAR(4000) NOT NULL
);

CREATE TABLE role (
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  name              VARCHAR(4000) NOT NULL
);

CREATE TABLE user_role (
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  user_id           INTEGER,
  role_id           INTEGER,
  FOREIGN KEY (user_id) REFERENCES app_user(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
);
