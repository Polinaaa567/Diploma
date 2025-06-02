-- CREATE DATABASE volunteering_kemsu
-- WITH
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'ru-RU'
--     LC_CTYPE = 'ru-RU'
-- ;

\c volunteering_kemsu;

SET CLIENT_ENCODING TO 'UTF8';
   
-- CREATE SEQUENCE IF NOT EXISTS events_event_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS events
-- (
--     event_id integer NOT NULL DEFAULT nextval('events_event_id_seq'::regclass),
--     name_event text COLLATE pg_catalog."default" NOT NULL,
--     description_event text COLLATE pg_catalog."default" NOT NULL,
--     address_event text COLLATE pg_catalog."default",
--     event_format text COLLATE pg_catalog."default" NOT NULL,
--     time_event text COLLATE pg_catalog."default" NOT NULL,
--     max_number_participants integer DEFAULT 0,
--     event_type text COLLATE pg_catalog."default",
--     age_restrictions integer,
--     number_points_event integer NOT NULL DEFAULT 0,
--     date_creation timestamp with time zone NOT NULL,
--     link_dobro_rf text COLLATE pg_catalog."default",
--     image bytea,
--     date_event timestamp with time zone NOT NULL,
--     CONSTRAINT events_pkey PRIMARY KEY (event_id)
-- );


-- insert into events (name_event, description_event, event_format, time_event, number_points_event, date_creation, date_event) values 
-- 	('День рождения лучших девчонок', 'Друзья подарили Нюше на день рождения замечательный подарок — шоколадную машину на вечном двигателе, которая делает плитки шоколада. Нюша, конечно же, сразу захотела шоколада, и чем больше, тем лучше.

-- На следующий день Нюша сделала столько шоколада, что затопила им всё. Смешарики попали под настоящее извержение. Откапывались кто чем: Крош и Ёжик копали лапами, Лосяш и Копатыч — лопатой, а Совунья с Кар-Карычем — якорем, похожим на кирку. Встретившись и обсудив ситуацию, друзья приняли решение, что надо выбираться. И чем быстрее — тем лучше, ведь шоколад на солнце тает и затапливает ходы! Наконец они находят вертикальную шахту, откуда видно небо, но подняться наверх нет никакой возможности. Отчаявшись, смешарики опустили головы. И тут сверху падает верёвка. Это же Пин! Он прилетел за ними на своём воздушном шаре.

-- Уже настал вечер. Солнце утопало в розовом небе, поглощавшем его с каждой минутой. Смешарики, летя на воздушном шаре Пина над кажущимся безграничным шоколадным морем, искали Нюшу. И вот Крош увидел виновницу катастрофы в шоколадном океане. Она сидела на синем ведре и объедалась вкусным и сладким лакомством. Пин немедленно опустил верёвку, а Крош поднял сладкоежку на борт. Оказалось, Нюша съела так много шоколада, что ей даже стало плохо. В конце Карыч говорит ей, что во всём нужно соблюдать меру.',
-- 'очно', '18.00', 45, '2003-04-10', '2023-01-23'
-- );

-- insert into events (name_event, description_event, event_format, time_event, number_points_event, date_creation, date_event) values 
-- 	('Лучшее Караоке', 'Взгляд твой как власть
-- Та, что разжигает страсть, Я, ты – Bad girl
-- Кто тебя сюда привел?
-- А Автограф свой ногтями на спине
-- Этой ночью можешь ты оставить мне

-- Детка, ты за мной иди
-- Я тот, кого так хочешь ты
-- Я вижу блеск в твоих глазах
-- Ты – моя звезда

-- А я твой номер один, baby, я твой номер один
-- Знаешь, а я твой номер один, baby, я твой номер один
-- А я твой номер один',
-- 'очно', '21.33', 100, '2023-04-10', '2025-03-14'
-- );


-- CREATE SEQUENCE IF NOT EXISTS roles_id_role_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS roles
-- (
--     id_role integer NOT NULL DEFAULT nextval('roles_id_role_seq'::regclass),
--     name_roles text COLLATE pg_catalog."default" NOT NULL,
--     CONSTRAINT roles_pkey PRIMARY KEY (id_role)
-- );

-- insert into roles (name_roles) values
-- ('Администратор');

-- insert into roles (name_roles) values
-- ('Пользователь');


-- ------------------------------

-- CREATE SEQUENCE IF NOT EXISTS users_id_user_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS users
-- (
--     id_user integer NOT NULL DEFAULT nextval('users_id_user_seq'::regclass),
--     last_name text COLLATE pg_catalog."default",
--     first_name text COLLATE pg_catalog."default",
--     patronymic text COLLATE pg_catalog."default",
--     number_phone text COLLATE pg_catalog."default",
--     email text COLLATE pg_catalog."default",
--     password text COLLATE pg_catalog."default" NOT NULL,
--     clothing_size text COLLATE pg_catalog."default",
--     age_stamp boolean,
--     fk_role_id integer NOT NULL,
--     date_creation timestamp with time zone NOT NULL,
--     CONSTRAINT users_pkey PRIMARY KEY (id_user),
--     CONSTRAINT users_fk_role_id_fkey FOREIGN KEY (fk_role_id)
--         REFERENCES roles (id_role) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
--         NOT VALID
-- );


-- CREATE SEQUENCE IF NOT EXISTS users_events_user_event_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;


-- CREATE TABLE IF NOT EXISTS users_events
-- (
--     user_event_id integer NOT NULL DEFAULT nextval('users_events_user_event_id_seq'::regclass),
--     fk_user_id integer NOT NULL,
--     fk_event_id integer NOT NULL,
--     date_creation timestamp with time zone NOT NULL,
--     stamp_participate boolean,
--     time_participate double precision,
--     CONSTRAINT users_events_pkey PRIMARY KEY (user_event_id),
--     CONSTRAINT users_events_fk_event_id_fkey FOREIGN KEY (fk_event_id)
--         REFERENCES public.events (event_id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
--         NOT VALID,
--     CONSTRAINT users_events_fk_user_id_fkey FOREIGN KEY (fk_user_id)
--         REFERENCES public.users (id_user) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
--         NOT VALID
-- );

-- ALTER TABLE events 
-- DROP COLUMN time_event;

-- insert into events (name_event, description_event, event_format, number_points_event, date_creation, date_event) values 
-- 	('Субботник для концерта крида', 'Драка за места у ног егорки. Кто выжил - тот и выиграл. Драться можно всем чем угодно: грабли, лопаты, метёлки, дети, старики. Да начнуться же голодные игры. Удачи, если же она вам понадобиться',
-- 'очно', 45, '2003-04-10', '16-04-2025 15:40:00'
-- );

-- ALTER TABLE users 
-- DROP COLUMN number_phone;

-- ALTER TABLE users RENAME COLUMN email TO login;


-- INSERT INTO users_events(
-- 	fk_user_id, fk_event_id, date_creation, stamp_participate, time_participate)
-- 	VALUES (?, ?, ?, ?, ?);

-- ALTER TABLE events 
-- DROP COLUMN age_restrictions;

-- ALTER TABLE events 
-- add COLUMN age_restrictions Integer DEFAULT 16;

-- ALTER TABLE users 
-- DROP COLUMN age_stamp;

-- ALTER TABLE users 
-- add COLUMN age_stamp TEXT DEFAULT '16-17';

-- ALTER TABLE users 
-- DROP COLUMN clothing_size;

-- ALTER TABLE users 
-- add COLUMN clothing_size TEXT DEFAULT 'xs';

-- ALTER TABLE users 
-- add COLUMN form_education TEXT DEFAULT 'очная';

-- ALTER TABLE users 
-- add COLUMN basis_education TEXT DEFAULT 'бюджет';

-- drop TABLE IF EXISTS users_events;

-- CREATE TABLE IF NOT EXISTS users_events
-- (
--     user_event_id integer NOT NULL DEFAULT nextval('users_events_user_event_id_seq'::regclass),
--     fk_user_id integer NOT NULL,
--     fk_event_id integer NOT NULL,
--     date_creation timestamp with time zone NOT NULL,
--     stamp_participate boolean,
--     time_participate double precision,
--     CONSTRAINT users_events_pkey PRIMARY KEY (user_event_id),
--     CONSTRAINT users_events_fk_event_id_fkey FOREIGN KEY (fk_event_id)
--         REFERENCES public.events (event_id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE CASCADE
--         NOT VALID,
--     CONSTRAINT users_events_fk_user_id_fkey FOREIGN KEY (fk_user_id)
--         REFERENCES public.users (id_user) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE CASCADE
--         NOT VALID
-- );


-- CREATE SEQUENCE IF NOT EXISTS levels_level_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS levels
-- (
--     level_id integer NOT NULL DEFAULT nextval('levels_level_id_seq'::regclass),
--     level_number integer NOT NULL,
--     max_number_points integer NOT NULL,
--     CONSTRAINT levels_pkey PRIMARY KEY (level_id)
-- );

-- INSERT INTO levels(
-- 	level_number, max_number_points)
-- 	VALUES (1, 1000);

-- INSERT INTO levels(
-- 	level_number, max_number_points)
-- 	VALUES (2, 2000);

-- INSERT INTO levels(
-- 	level_number, max_number_points)
-- 	VALUES (3, 3000);

-- INSERT INTO levels(
-- 	level_number, max_number_points)
-- 	VALUES (4, 4000);

-- CREATE SEQUENCE IF NOT EXISTS users_points_user_points_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS users_points
-- (
--     user_points_id integer NOT NULL DEFAULT nextval('users_points_user_points_id_seq'::regclass),
--     fk_user_id integer NOT NULL,
--     points integer NOT NULL DEFAULT 0,
--     date_change timestamp with time zone NOT NULL,
--     CONSTRAINT users_points_pkey PRIMARY KEY (user_points_id),
--     CONSTRAINT users_points_fk_user_id_fkey FOREIGN KEY (fk_user_id)
--         REFERENCES users (id_user) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE CASCADE
--         NOT VALID
-- );


-- insert INTO users (login, PASSWORD, fk_role_id, date_creation) VALUES ('admin', '$2a$14$2BV0F0C3Io5pl1jpJxkNkOsPCt3y45V/tISsdFz80jdeBsip/hJkG', 1, '2025-04-04 15:30:33');

-- ALTER TABLE events 
-- add COLUMN is_participation boolean DEFAULT false;

-- CREATE SEQUENCE IF NOT EXISTS news_news_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS news
-- (
--     news_id integer NOT NULL DEFAULT nextval('news_news_id_seq'::regclass),
--     headline_news text COLLATE pg_catalog."default" NOT NULL,
--     description_news text COLLATE pg_catalog."default" NOT NULL,
--     date_creation timestamp with time zone NOT NULL,
--     CONSTRAINT news_pkey PRIMARY KEY (news_id)
-- );

-- insert into news (headline_news, description_news, date_creation) values 
-- 	('Лазарев признался что он ...', 'Как вам всем известно, Сергей Лазарев обладает мощной харизмой. Он сражает всех женщин наповал. 
--      Но он признался, что его сердце всё ещё свободно (Он просто не встретил меня 😄🤡))))', '16-04-2025 15:40:00'
-- );

-- CREATE SEQUENCE IF NOT EXISTS image_news_photo_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS image_news
-- (
--     photo_id integer NOT NULL DEFAULT nextval('image_news_photo_id_seq'::regclass),
--     fk_news_id integer NOT NULL,
--     image_data bytea NOT NULL,
--     CONSTRAINT image_news_pkey PRIMARY KEY (photo_id),
--     CONSTRAINT image_news_fk_news_id_fkey FOREIGN KEY (fk_news_id)
--         REFERENCES public.news (news_id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE CASCADE
--         NOT VALID
-- );

-- CREATE SEQUENCE IF NOT EXISTS types_id_type_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS types
-- (
--     id_type integer NOT NULL DEFAULT nextval('types_id_type_seq'::regclass),
--     name text COLLATE pg_catalog."default" NOT NULL,
--     CONSTRAINT types_pkey PRIMARY KEY (id_type)
-- );

-- ALTER TABLE events 
-- DROP COLUMN event_type;

-- ALTER TABLE events 
-- add COLUMN fk_type integer;

-- ALTER TABLE events 
-- ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);

-- ALTER TABLE events 
-- ADD CONSTRAINT events_fk_type_fkey FOREIGN KEY (fk_type)
--     REFERENCES public.types (id_type) MATCH SIMPLE
--     ON UPDATE NO ACTION
--     ON DELETE NO ACTION
--     NOT VALID;

-- ALTER TABLE users_points 
-- add COLUMN fk_event_id integer;

-- ALTER TABLE users_points 
-- ADD CONSTRAINT users_points_pkey PRIMARY KEY (user_points_id);

-- ALTER TABLE users_points 
-- ADD CONSTRAINT users_points_fk_event_id_fkey FOREIGN KEY (fk_event_id)
--         REFERENCES public.events (event_id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
--         NOT VALID;

-- CREATE SEQUENCE IF NOT EXISTS lessons_lesson_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS lessons
-- (
--     lesson_id integer NOT NULL DEFAULT nextval('lessons_lesson_id_seq'::regclass),
--     headline text COLLATE pg_catalog."default" NOT NULL,
--     link text COLLATE pg_catalog."default" NOT NULL,
--     number_points integer NOT NULL DEFAULT 5,
--     date_creation timestamp with time zone NOT NULL,
--     CONSTRAINT lessons_pkey PRIMARY KEY (lesson_id)
-- );

-- CREATE SEQUENCE IF NOT EXISTS center_center_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS center
-- (
--     center_id integer NOT NULL DEFAULT nextval('center_center_id_seq'::regclass),
--     name text NOT NULL,
--     description text NOT NULL,
--     image_url text,
--     date_change timestamp with time zone NOT NULL,
--     contacts text,
--     address text ,
--     CONSTRAINT center_pkey PRIMARY KEY (center_id)
-- );

-- INSERT INTO center(
--     name, description, date_change, contacts , address
-- ) VALUES (
--     'Волонтёрский центр КемГУ | Добро.Центр КемГУ', 'Новая команда КемГУ!
-- Именно здесь собрались те люди, которые готовы работать в любом направлении и помогать общественности!
-- Стань частью новой организации!', '01-05-2025 12:40:00', 'https://vk.com/dobro_kemsu?from=groups', 'г. Кемерово, пр. Советский 73, ауд. 2101'
-- );


-- CREATE SEQUENCE IF NOT EXISTS faq_faq_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;


-- CREATE TABLE IF NOT EXISTS faq
-- (
--     faq_id integer NOT NULL DEFAULT nextval('faq_faq_id_seq'::regclass),
--     question text NOT NULL,
--     answer text NOT NULL,
--     CONSTRAINT faq_pkey PRIMARY KEY (faq_id)
-- );

-- insert into faq(question, answer) values ('ква', 'кря');

-- ALTER TABLE lessons 
-- add COLUMN description TEXT;


-- ALTER TABLE users_points 
-- ADD CONSTRAINT users_points_fk_lesson_id_fkey FOREIGN KEY (fk_lesson_id)
--         REFERENCES lessons (lesson_id) MATCH SIMPLE
--         ON UPDATE NO ACTION
--         ON DELETE NO ACTION
--         NOT VALID;

-- DROP TABLE users_points;

-- CREATE SEQUENCE IF NOT EXISTS users_points_user_points_id_seq
--     INCREMENT 1
--     START 1
--     MINVALUE 1
--     MAXVALUE 2147483647
--     CACHE 1;

-- CREATE TABLE IF NOT EXISTS users_points
-- (
--     user_points_id integer NOT NULL DEFAULT nextval('users_points_user_points_id_seq'::regclass),
--     fk_user_id integer NOT NULL,
--     points integer NOT NULL DEFAULT 0,
--     date_change timestamp with time zone NOT NULL,
--     fk_event_id integer,
--     fk_lesson_id integer,
--     CONSTRAINT users_points_pkey PRIMARY KEY (user_points_id),
--     CONSTRAINT users_points_fk_event_id_fkey FOREIGN KEY (fk_event_id)
--         REFERENCES public.events (event_id) MATCH SIMPLE
--         ON UPDATE CASCADE
--         ON DELETE CASCADE
--         NOT VALID,
--     CONSTRAINT users_points_fk_lesson_id_fkey FOREIGN KEY (fk_lesson_id)
--         REFERENCES public.lessons (lesson_id) MATCH SIMPLE
--         ON UPDATE CASCADE
--         ON DELETE CASCADE
--         NOT VALID,
--     CONSTRAINT users_points_fk_user_id_fkey FOREIGN KEY (fk_user_id)
--         REFERENCES public.users (id_user) MATCH SIMPLE
--         ON UPDATE CASCADE
--         ON DELETE CASCADE
--         NOT VALID
-- );

DROP TABLE certificate;

CREATE SEQUENCE IF NOT EXISTS certificate_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS certificate
(
    id integer NOT NULL DEFAULT nextval('certificate_id_seq'::regclass),
    fk_user_id integer NOT NULL,
    image_url text COLLATE pg_catalog."default" NOT NULL,
    fk_event_id integer,
    CONSTRAINT certificate_pkey PRIMARY KEY (id),
    CONSTRAINT certificate_fk_event_id_fkey FOREIGN KEY (fk_event_id)
        REFERENCES public.events (event_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
        NOT VALID,
    CONSTRAINT certificate_fk_user_id_fkey FOREIGN KEY (fk_user_id)
        REFERENCES public.users (id_user) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);