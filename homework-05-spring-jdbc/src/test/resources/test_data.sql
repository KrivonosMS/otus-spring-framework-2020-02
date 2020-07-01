insert into author (name) values ('Александр Пушкин');
insert into author (name) values ('Николай Гоголь');
insert into author (name) values ('Фёдор Тютчев');

insert into genre (type) values ('Классическая проза');
insert into genre (type) values ('Литература 19 века');
insert into genre (type) values ('Русская классика');
insert into genre (type) values ('Древнерусская литература');

insert into book (title, author_id, genre_id, is_deleted) values ('Повести Белкина (сборник)', 1, 2, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('Евгений Онегин', 1, 1, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('Сказка о царе Салтане', 1, 4, 0);

insert into book (title, author_id, genre_id, is_deleted) values ('Вечера на хуторе близ Диканьки', 2, 1, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('Петербургские повести', 2, 3, 0);

insert into book (title, author_id, genre_id, is_deleted) values ('Федор Тютчев: Стихи', 3, 1, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('Федор Тютчев: Стихи детям', 3, 1, 0);