insert into author (name) values ('��������� ������');
insert into author (name) values ('������� ������');
insert into author (name) values ('Ը��� ������');

insert into genre (type) values ('������������ �����');
insert into genre (type) values ('���������� 19 ����');
insert into genre (type) values ('������� ��������');
insert into genre (type) values ('������������� ����������');

insert into book (title, author_id, genre_id, is_deleted) values ('������� ������� (�������)', 1, 2, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('������� ������', 1, 1, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('������ � ���� �������', 1, 4, 0);

insert into book (title, author_id, genre_id, is_deleted) values ('������ �� ������ ���� ��������', 2, 1, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('������������� �������', 2, 3, 0);

insert into book (title, author_id, genre_id, is_deleted) values ('����� ������: �����', 3, 1, 0);
insert into book (title, author_id, genre_id, is_deleted) values ('����� ������: ����� �����', 3, 1, 0);