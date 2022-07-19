create table "users_endpoints"
(
    uid_user     varchar not null REFERENCES "users" ("uid") ON DELETE CASCADE,
    uid_endpoint varchar not null REFERENCES "endpoints" ("uid") ON DELETE CASCADE,
    permission   boolean not null,

    PRIMARY KEY (uid_user, uid_endpoint)
);

INSERT INTO "users_endpoints"("uid_user", "uid_endpoint", "permission")
VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e5', '26e438e0-f39e-4656-8430-e10122d7e0e0', true);

INSERT INTO "users_endpoints"("uid_user", "uid_endpoint", "permission")
VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e5', '26e438e0-f39e-4656-8430-e10122d7e0e1', false);

-- INSERT INTO "users_endpoints"("uid_user", "uid_endpoint", "permission")
-- VALUES ('26e438e0-f39e-4656-8430-e10122d7e0e5', '26e438e0-f39e-4656-8430-e10122d7e0e2', false);