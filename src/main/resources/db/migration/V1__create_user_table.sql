create table "users"
(
    uid      varchar     not null unique primary key,
    username varchar(40) not null,
    password varchar not null
);

INSERT INTO "users"("uid", "username", "password")
VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e5', 'user', '$2a$10$j.TVxZrEERAkIV0CpISEXOqVm1wE6xx8uzCdFQPBqopbmCn5lm6kO');

