drop table if exists author_exhibit;
drop table if exists exhibit;
drop table if exists author;
drop table if exists exhibit_type;
drop table if exists museum;
drop table if exists museum_type;



create table if not exists author(
	id bigserial primary key,
	full_name varchar not null
);

create table if not exists exhibit_type(
	id bigserial primary key,
	name varchar not null
);

create table if not exists museum_type(
	id bigserial primary key,
	name varchar not null
);

create table if not exists museum(
	id bigserial primary key,
	name varchar not null,
	address varchar not null,
	full_name_of_the_head varchar not null,
	phone_number varchar not null,
	museum_type_id int references museum_type(id) on delete cascade check(museum_type_id>0)
);

create table if not exists exhibit(
	id bigserial primary key,
	name varchar not null,
	on_exposition boolean not null,
	museum_id int references museum(id) on delete cascade check(museum_id>0),
	exhibit_type_id int references exhibit_type(id) on delete cascade check(exhibit_type_id>0)
);

create table if not exists author_exhibit(
	author_id int references author(id) on delete cascade check(author_id>0),
	exhibit_id int references exhibit(id) on delete cascade check(exhibit_id>0),
	primary key(author_id, exhibit_id)
);



insert into author(full_name) values
('Микеланджело'),
('Винсент ван Гог'),
('Леонардо да Винчи'),
('Академия Коммунального хозяйства'),
('Специалисты УКВЗ'),
('Федотов А.В.'),
('Леонидов'),
('Конструкторское бюро приборостроения'),
('Вильгельмом Маузер, Паул Маузер'),
('Группа конструкторов под руководством Евгения Драгунова'),
('А. И. Шилин, П. П. Поляков, А. А. Дубинин'),
('Ленинградское Специальное конструкторское бюро № 4 (СКБ-4) под руководством Б.И.Шавырина'),
('ОКБ-16'),
('Василий Грязев, Аркадий Шипунов');

insert into exhibit_type(name) values
('Трамвай'),
('Автомобиль'),
('Мотоцикл'),
('Картина'),
('Оружие боевое'),
('Артиллерийское оружие, пушки');

insert into museum_type(name) values
('Общеисторический'),
('Национальной истории'),
('Региональной истории'),
('Истории отдельных периодов или истории событий, феноменов'),
('Узкоспециализированный'),
('Художественный'),
('Литературный'),
('Техники'),
('Оружия');

insert into museum(name, address, full_name_of_the_head, phone_number, museum_type_id) values
('Музей военной и автомобильной техники УГМК', 'ул. Александра Козицына, 2, Верхняя Пышма 624090, Россия', 'Алексеенко Глеб Сергеевич', '83436847218', 1),
('Государственный историко-мемориальный музей-заповедник Сталинградская битва', 'ул. им. Маршала Чуйкова, 47, Волгоград 400005, Россия', 'Цыганков Андрей Викторович', '88442550151', 2),
('Третьяковская галерея в Лаврушинском переулке', 'Лаврушинский переулок, д. 10, Москва 119017, Россия', 'Неизвестен', '84959510727', 6),
('Музей трамваев', 'ул. Дмитрия Донского, 15, Коломна 140400, Россия', 'Неизвестен', '79169550147', 8),
('Тульский государственный музей оружия', 'ул. Октябрьская, 2, Тула, Тульская обл., 300041', 'Калугина Надежда Ивановна', '84872472241', 9);

insert into exhibit(name, on_exposition, museum_id, exhibit_type_id) values
('КТМ-1', true, 4, 1),
('71-623', true, 4, 1),
('КТМ-5М3 / 71-605', false, 4, 1),
('Моно Лиза', true, 3, 4),
('Звездная ночь', true, 3, 4),
('Сотворение Адама', false, 3, 4),
('КТМ-5М «Урал', true, 4, 1),
('ПП-90М1', true, 5, 5),
('ПП-2000', true, 5, 5),
('Винтовка Маузера обр. 1871 г', true, 5, 5),
('Винтовка снайперская СВД', false, 5, 5),
('РП-46', true, 5, 5),
('Миномет батальонный обр.1937 г. учебный', true, 5, 6),
('Пушка Н-57 1947 г', true, 5, 6),
('23-мм шестиствольная авиационная пушка ГШ-6-23М', false, 5, 6);

insert into author_exhibit(author_id, exhibit_id) values
(1, 6),
(2, 5),
(3, 4),
(4, 1),
(5, 2),
(6, 3),
(7, 7),
(8, 8),
(8, 9),
(9, 10),
(10, 11),
(11, 12),
(12, 13),
(13, 14),
(14, 15);



select * from author;
select * from exhibit_type;
select * from museum_type;
select * from museum;
select * from exhibit;
select * from author_exhibit;