CREATE DATABASE volunteering_kemsu
WITH
    ENCODING = 'UTF8'
    LC_COLLATE = 'ru-RU'
    LC_CTYPE = 'ru-RU'
;

\c volunteering_kemsu;

SET CLIENT_ENCODING TO 'UTF8';
   
CREATE SEQUENCE IF NOT EXISTS events_event_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS events
(
    event_id integer NOT NULL DEFAULT nextval('events_event_id_seq'::regclass),
    name_event text COLLATE pg_catalog."default" NOT NULL,
    description_event text COLLATE pg_catalog."default" NOT NULL,
    address_event text COLLATE pg_catalog."default",
    event_format text COLLATE pg_catalog."default" NOT NULL,
    time_event text COLLATE pg_catalog."default" NOT NULL,
    max_number_participants integer DEFAULT 0,
    event_type text COLLATE pg_catalog."default",
    age_restrictions integer,
    number_points_event integer NOT NULL DEFAULT 0,
    date_creation timestamp with time zone NOT NULL,
    link_dobro_rf text COLLATE pg_catalog."default",
    image bytea,
    date_event timestamp with time zone NOT NULL,
    CONSTRAINT events_pkey PRIMARY KEY (event_id)
);


insert into events (name_event, description_event, event_format, time_event, number_points_event, date_creation, date_event) values 
	('День рождения лучших девчонок', 'Друзья подарили Нюше на день рождения замечательный подарок — шоколадную машину на вечном двигателе, которая делает плитки шоколада. Нюша, конечно же, сразу захотела шоколада, и чем больше, тем лучше.

На следующий день Нюша сделала столько шоколада, что затопила им всё. Смешарики попали под настоящее извержение. Откапывались кто чем: Крош и Ёжик копали лапами, Лосяш и Копатыч — лопатой, а Совунья с Кар-Карычем — якорем, похожим на кирку. Встретившись и обсудив ситуацию, друзья приняли решение, что надо выбираться. И чем быстрее — тем лучше, ведь шоколад на солнце тает и затапливает ходы! Наконец они находят вертикальную шахту, откуда видно небо, но подняться наверх нет никакой возможности. Отчаявшись, смешарики опустили головы. И тут сверху падает верёвка. Это же Пин! Он прилетел за ними на своём воздушном шаре.

Уже настал вечер. Солнце утопало в розовом небе, поглощавшем его с каждой минутой. Смешарики, летя на воздушном шаре Пина над кажущимся безграничным шоколадным морем, искали Нюшу. И вот Крош увидел виновницу катастрофы в шоколадном океане. Она сидела на синем ведре и объедалась вкусным и сладким лакомством. Пин немедленно опустил верёвку, а Крош поднял сладкоежку на борт. Оказалось, Нюша съела так много шоколада, что ей даже стало плохо. В конце Карыч говорит ей, что во всём нужно соблюдать меру.',
'очно', '18.00', 45, '2003-04-10', '2023-01-23'
);

insert into events (name_event, description_event, event_format, time_event, number_points_event, date_creation, date_event) values 
	('Лучшее Караоке', 'Взгляд твой как власть
Та, что разжигает страсть, Я, ты – Bad girl
Кто тебя сюда привел?
А Автограф свой ногтями на спине
Этой ночью можешь ты оставить мне

Детка, ты за мной иди
Я тот, кого так хочешь ты
Я вижу блеск в твоих глазах
Ты – моя звезда

А я твой номер один, baby, я твой номер один
Знаешь, а я твой номер один, baby, я твой номер один
А я твой номер один',
'очно', '21.33', 100, '2023-04-10', '2025-03-14'
);
