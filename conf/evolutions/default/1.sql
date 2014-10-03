
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


# --- !Downs

Drop TABLE books;

