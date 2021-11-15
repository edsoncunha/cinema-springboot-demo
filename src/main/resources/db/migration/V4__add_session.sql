CREATE TABLE movie_sessions
  (
     id                         SERIAL,
     movie_id                   int8 NOT NULL,
     day_of_week                int4 NOT NULL,
     session_time               time NOT NULL,
     price                      numeric(5, 2) not null,
     room                       varchar(255) not null,
     capacity                   int4 not null,
     CONSTRAINT movie_sessions_movie_fkey FOREIGN KEY (movie_id) REFERENCES movie(id),
     CONSTRAINT movie_sessions_uq_fields UNIQUE (movie_id, day_of_week, session_time, room)
  );

