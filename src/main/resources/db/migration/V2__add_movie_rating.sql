CREATE TABLE movie_rating
  (
     id                         SERIAL,
     movie_id                   int8 not null,
     rating                     int4 NOT NULL,
     CONSTRAINT movie_fkey FOREIGN KEY (movie_id) REFERENCES movie(id)
  );