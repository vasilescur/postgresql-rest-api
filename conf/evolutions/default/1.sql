
---Database schema is retrieved from following URL, and only part of the database schema is used for test purpose
---http://www.commandprompt.com/ppbook/booktown.sql
---http://www.commandprompt.com/ppbook/

# --- !Ups

CREATE TABLE alternate_stock (
    isbn text,
    cost numeric(5,2),
    retail numeric(5,2),
    stock integer
);

CREATE TABLE authors (
    id integer NOT NULL,
    last_name text,
    first_name text
);


CREATE TABLE books (
    id integer NOT NULL,
    title text NOT NULL,
    author_id integer,
    subject_id integer
);

CREATE TABLE book_backup (
    id integer,
    title text,
    author_id integer,
    subject_id integer
);

CREATE TABLE book_queue (
    title text NOT NULL,
    author_id integer,
    subject_id integer,
    approved boolean
);



CREATE TABLE customers (
    id integer NOT NULL,
    last_name text,
    first_name text
);


CREATE TABLE daily_inventory (
    isbn text,
    is_stocked boolean
);

CREATE TABLE editions (
    isbn text NOT NULL,
    book_id integer,
    edition integer,
    publisher_id integer,
    publication date,
    type character(1),
    CONSTRAINT integrity CHECK (((book_id IS NOT NULL) AND (edition IS NOT NULL)))
);

CREATE TABLE states (
    id integer NOT NULL,
    name text,
    abbreviation character(2)
);


CREATE TABLE stock (
    isbn text NOT NULL,
    cost numeric(5,2),
    retail numeric(5,2),
    stock integer
);

CREATE TABLE test_data_type (
    c_smallint smallint,
    c_integer integer,
    c_bigint bigint,
    c_decimal decimal(4,2),
    c_real real,
    c_character  character(2),
    c_text text,
    c_varchar varchar,
    c_boolean boolean,
    c_timestamp timestamp,
    c_date date,
    c_time time
);



INSERT INTO books (id, title, author_id, subject_id)
VALUES
(7808, 'The Shining', 4156,9),
(4513, 'Dune', 1866,15),
(4267, '2001: A Space Odyssey', 2001,15),
(1608, 'The Cat in the Hat', 1809,2),
(1590, 'Bartholomew and the Oobleck', 1809,2),
(25908, 'Franklin in the Dark', 15990,2),
(1501, 'Goodnight Moon', 2031,2),
(190, 'Little Women', 16,6),
(1234, 'The Velveteen Rabbit', 25041,3),
(2038, 'Dynamic Anatomy', 1644,0),
(156, 'The Tell-Tale Heart', 115,9),
(41472, 'Practical PostgreSQL', 1212,4),
(41473, 'Programming Python', 7805,4),
(41477, 'Learning Python', 7805,4),
(41478, 'Perl Cookbook', 7806,4);


INSERT INTO test_data_type(
            c_smallint, c_integer, c_bigint, c_decimal, c_real,
            c_character, c_text, c_varchar, c_boolean,
            c_timestamp, c_date, c_time)
    VALUES (32767, 2147483647, 9223372036854775807, 4.99, 29999.99,
            'ab', 'text', 'var', true,
            '2014-10-12 10:23:54', '2014-10-12', '10:23:54');




# --- !Downs

Drop TABLE books;
Drop TABLE test_data_type;


