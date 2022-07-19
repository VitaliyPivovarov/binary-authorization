create table "endpoints"
(
    uid   varchar       not null unique primary key,
    uri   varchar(2048) not null,
    index integer       not null
);

INSERT INTO "endpoints"("uid", "uri", "index")
VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e0', 'GET/gateway/something', 0);

INSERT INTO "endpoints"("uid", "uri", "index")
VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e1', 'POST/gateway/something', 1);

INSERT INTO "endpoints"("uid", "uri", "index")
VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e2', 'PUT/gateway/something', 2);