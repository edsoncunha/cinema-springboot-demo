CREATE TABLE movie
  (
     id                         SERIAL,
     title                       VARCHAR(255) NOT NULL,
     plot                       TEXT NOT NULL,
     customers_rating           NUMERIC(3, 2) NULL,
     number_of_customer_ratings INT4 NOT NULL DEFAULT 0,
     imdb_rating                NUMERIC(3, 2) NULL,
     rotten_tomatoes_rating     NUMERIC(3, 2) NULL,
     metacritic_rating          NUMERIC(3, 2) NULL,
     runtime_in_minutes         INT4 NULL,
     imdb_id                    VARCHAR(255) NOT NULL,
     last_update                TIMESTAMP NOT NULL DEFAULT now(),
     CONSTRAINT movie_pkey PRIMARY KEY (id),
     CONSTRAINT unique_imdb_id UNIQUE (imdb_id)
  );

INSERT INTO public.movie (title,plot,customers_rating,number_of_customer_ratings,imdb_rating,rotten_tomatoes_rating,metacritic_rating,runtime_in_minutes,imdb_id,last_update) VALUES
	 ('2 Fast 2 Furious','Former cop Brian O''Conner is called upon to bust a dangerous criminal and he recruits the help of a former childhood friend and street racer who has a chance to redeem himself.',NULL,0,5.90,NULL,NULL,107,'tt0322259','2021-11-14 16:35:00.797397'),
	 ('The Fast and the Furious: Tokyo Drift','A teenager becomes a major competitor in the world of drift racing after moving in with his father in Tokyo to avoid a jail sentence in America.',NULL,0,6.00,NULL,NULL,104,'tt0463985','2021-11-14 16:35:00.820806'),
	 ('Fast & Furious','Brian O''Conner, back working for the FBI in Los Angeles, teams up with Dominic Toretto to bring down a heroin importer by infiltrating his operation.',NULL,0,6.60,NULL,NULL,107,'tt1013752','2021-11-14 16:35:00.85482'),
	 ('Fast Five','Dominic Toretto and his crew of street racers plan a massive heist to buy their freedom while in the sights of a powerful Brazilian drug lord and a dangerous federal agent.',NULL,0,7.30,NULL,NULL,130,'tt1596343','2021-11-14 16:35:00.653854'),
	 ('Furious 6','Hobbs has Dominic and Brian reassemble their crew to take down a team of mercenaries: Dominic unexpectedly gets sidetracked with facing his presumed deceased girlfriend, Letty.',NULL,0,7.00,NULL,NULL,130,'tt1905041','2021-11-14 16:35:00.69807'),
	 ('Fast & Furious 7','Deckard Shaw seeks revenge against Dominic Toretto and his family for his comatose brother.',NULL,0,7.10,NULL,NULL,137,'tt2820852','2021-11-14 16:35:00.721141'),
	 ('The Fate of the Furious','When a mysterious woman seduces Dominic Toretto into the world of terrorism and a betrayal of those closest to him, the crew face trials that will test them as never before.',NULL,0,6.60,NULL,NULL,136,'tt4630562','2021-11-14 16:35:00.745548'),
	 ('The Fast and the Furious','Los Angeles police officer Brian O''Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.',NULL,0,6.80,NULL,NULL,106,'tt0232500','2021-11-14 16:35:00.769101');