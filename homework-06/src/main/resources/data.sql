insert into author (name) values ('��������� ������');
insert into author (name) values ('������� ������');
insert into author (name) values ('Ը��� ������');

insert into genre (type) values ('������������ �����');
insert into genre (type) values ('���������� 19 ����');
insert into genre (type) values ('������� ��������');
insert into genre (type) values ('������������� ����������');

insert into book (title, author_id, genre_id) values ('������� ������� (�������)', 1, 2);
insert into book (title, author_id, genre_id) values ('������� ������', 1, 1);
insert into book (title, author_id, genre_id) values ('������ � ���� �������', 1, 4);

insert into book (title, author_id, genre_id) values ('������ �� ������ ���� ��������', 2, 1);
insert into book (title, author_id, genre_id) values ('������������� �������', 2, 3);

insert into book (title, author_id, genre_id) values ('����� ������: �����', 3, 1);
insert into book (title, author_id, genre_id) values ('����� ������: ����� �����', 3, 1);