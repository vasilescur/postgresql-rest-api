
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
(7809, 'Franklin in the Dark',	4159,	2);


# --- !Downs

Drop TABLE books;

