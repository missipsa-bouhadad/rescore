-- Student residences 
CREATE TABLE residences (
    id      BIGSERIAL PRIMARY KEY,
    city_id BIGINT       NOT NULL REFERENCES cities (id),
    name    VARCHAR(200) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type    VARCHAR(20)  NOT NULL CHECK (type IN ('CROUS', 'PRIVATE')),
    UNIQUE (city_id, name)
);

CREATE INDEX idx_residences_city_id ON residences (city_id);
