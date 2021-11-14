CREATE TABLE movie
  (
     id                         SERIAL,
     title                       VARCHAR(255) NOT NULL,
     plot                       TEXT NOT NULL,
     release_date               DATE NULL,
     customers_rating           NUMERIC(3, 2) NULL,
     number_of_customer_ratings INT4 NOT NULL DEFAULT 0,
     imdb_rating                NUMERIC(3, 2) NULL,
     rotten_tomatoes_rating     NUMERIC(3, 2) NULL,
     metacritic_rating          NUMERIC(3, 2) NULL,
     runtime_in_minutes         INT4 NULL,
     imdb_id                    VARCHAR(255) NOT NULL,
     CONSTRAINT movie_pkey PRIMARY KEY (id),
     CONSTRAINT unique_imdb_id UNIQUE (imdb_id)
  );

INSERT INTO movie (title, plot, imdb_id) VALUES ('The Fast and the Furious', 'Not available', 'tt0232500');
INSERT INTO movie (title, plot, imdb_id) VALUES ('2 Fast 2 Furious', 'Not available', 'tt0322259');
INSERT INTO movie (title, plot, imdb_id) VALUES ('The Fast and the Furious: Tokyo Drift', 'Not available', 'tt0463985');
INSERT INTO movie (title, plot, imdb_id) VALUES ('Fast & Furious', 'Not available', 'tt1013752');
INSERT INTO movie (title, plot, imdb_id) VALUES ('Fast Five', 'Not available', 'tt1596343');
INSERT INTO movie (title, plot, imdb_id) VALUES ('Fast & Furious 6', 'Not available', 'tt1905041');
INSERT INTO movie (title, plot, imdb_id) VALUES ('Furious 7', 'Not available', 'tt2820852');
INSERT INTO movie (title, plot, imdb_id) VALUES ('The Fate of the Furious', 'Not available', 'tt4630562');