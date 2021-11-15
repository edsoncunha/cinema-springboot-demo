CREATE TABLE cinema_admins
  (
     id                         SERIAL,
     username                   varchar(255) NOT NULL,
     password                   varchar(255) NOT NULL,
     CONSTRAINT cinema_admins_unique_username UNIQUE (username)
  );

INSERT INTO cinema_admins (username, password) VALUES ('admin', '$2a$10$m6qgwdy1zDEjkNVvFY/ok.gTgSsu3khKyU5bZ5GVZhJd4Jk.rMT4u')