-- Users
CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    university_id BIGINT       NOT NULL REFERENCES universities (id),
    residence_id  BIGINT       NOT NULL REFERENCES residences (id),
    email         VARCHAR(255) NOT NULL UNIQUE CHECK (email = LOWER(email)),
    password_hash VARCHAR(255) NOT NULL,
    first_name    VARCHAR(100) NOT NULL,
    status        VARCHAR(20)  NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'ACTIVE')),
    role          VARCHAR(20)  NOT NULL DEFAULT 'PLAYER' CHECK (role IN ('PLAYER', 'RESIDENCE_MANAGER', 'ADMIN')),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    verified_at   TIMESTAMPTZ,
    deleted_at    TIMESTAMPTZ
);

CREATE INDEX idx_users_university_id ON users (university_id);
CREATE INDEX idx_users_residence_id ON users (residence_id);
