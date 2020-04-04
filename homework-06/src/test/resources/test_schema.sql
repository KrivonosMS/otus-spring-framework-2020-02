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
  author_id     INTEGER,
  genre_id      INTEGER,
  FOREIGN KEY (author_id) REFERENCES author(id),
  FOREIGN KEY (genre_id) REFERENCES genre(id)
);