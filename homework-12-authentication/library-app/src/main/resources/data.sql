insert into author (name) values ('Александр Пушкин');
insert into author (name) values ('Николай Гоголь');
insert into author (name) values ('Фёдор Тютчев');

insert into genre (type) values ('Классическая проза');
insert into genre (type) values ('Литература 19 века');
insert into genre (type) values ('Русская классика');
insert into genre (type) values ('Древнерусская литература');

insert into book (title, author_id, genre_id) values ('Повести Белкина (сборник)', 1, 2);
insert into comment (comment, creation_date, book_id) values ('комментарий 1', sysdate, 1);
insert into comment (comment, creation_date, book_id) values ('комментарий 2', sysdate, 1);
insert into book (title, author_id, genre_id) values ('Евгений Онегин', 1, 1);
insert into comment (comment, creation_date, book_id) values ('комментарий 1', sysdate, 2);
insert into comment (comment, creation_date, book_id) values ('комментарий 2', sysdate, 2);
insert into comment (comment, creation_date, book_id) values ('комментарий 3', sysdate, 2);
insert into book (title, author_id, genre_id) values ('Сказка о царе Салтане', 1, 4);
insert into comment (comment, creation_date, book_id) values ('комментарий 1', sysdate, 3);

insert into book (title, author_id, genre_id) values ('Вечера на хуторе близ Диканьки', 2, 1);
insert into comment (comment, creation_date, book_id) values ('комментарий 1', sysdate, 4);
insert into comment (comment, creation_date, book_id) values ('комментарий 2', sysdate, 4);
insert into book (title, author_id, genre_id) values ('Петербургские повести', 2, 3);

insert into book (title, author_id, genre_id) values ('Федор Тютчев: Стихи', 3, 1);
insert into book (title, author_id, genre_id) values ('Федор Тютчев: Стихи детям', 3, 1);
insert into comment (comment, creation_date, book_id) values ('комментарий 1', sysdate, 7);

insert into user (login, password) values ('user', '$2a$10$VlUyEYUugpFLph9wP3M1juQXkt7R0Cwiyi.VqSNGm1ILLhICfntSW');
insert into user (login, password) values ('admin', '$2a$10$12Kf286jLClqDETm9pVz6OfKfKF6sp03L8L1hdtc92tp0BfUYHEYS');

insert into role (name) values ('AdminRole');
insert into role (name) values ('UserRole');

insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (2, 1);
insert into user_role (user_id, role_id) values (2, 2);

