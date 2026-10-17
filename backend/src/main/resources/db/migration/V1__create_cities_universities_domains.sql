-- Cities where the app is available
CREATE TABLE cities (
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    slug   VARCHAR(100) NOT NULL UNIQUE,
    region VARCHAR(100) NOT NULL,
    active BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Universities and the city they are in
CREATE TABLE universities (
    id         BIGSERIAL PRIMARY KEY,
    city_id    BIGINT       NOT NULL REFERENCES cities (id),
    name       VARCHAR(200) NOT NULL,
    short_name VARCHAR(50)  NOT NULL
);

CREATE INDEX idx_universities_city_id ON universities (city_id);

-- Student email domains allowed to register
CREATE TABLE allowed_email_domains (
    id            BIGSERIAL PRIMARY KEY,
    university_id BIGINT       NOT NULL REFERENCES universities (id),
    domain        VARCHAR(255) NOT NULL UNIQUE
);

CREATE INDEX idx_allowed_email_domains_university_id ON allowed_email_domains (university_id);
