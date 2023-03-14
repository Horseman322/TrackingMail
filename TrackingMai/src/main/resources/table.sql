CREATE TABLE mail
(
    id                  SERIAL PRIMARY KEY UNIQUE,
    type                TEXT,
    recipient_index     INTEGER,
    recipient_address   TEXT,
    recipient_name      TEXT
);


CREATE TABLE posts
(
    index       SERIAL PRIMARY KEY UNIQUE,
    name        TEXT,
    address     TEXT
);


CREATE TABLE tracking
(
    id          SERIAL PRIMARY KEY UNIQUE,
    mail_id  INTEGER REFERENCES mail(id),
    post_index  INTEGER REFERENCES posts(index),
    date_time   TIMESTAMP,
    status      TEXT
);