
---http://www.commandprompt.com/ppbook/booktown.sql
---http://www.commandprompt.com/ppbook/

# --- !Ups

CREATE TABLE books (
    id integer NOT NULL,
    title text NOT NULL,
    author_id integer,
    subject_id integer
);




INSERT INTO books (id, title, author_id, subject_id)
VALUES
(7808, 'The Shining',	4156,	9),
(4513, 'Dune',	1866,	15);


# --- !Downs

Drop TABLE books;

