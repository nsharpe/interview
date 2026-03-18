
CREATE TABLE users.external_user (
    id BIGSERIAL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    creation_timestamp TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    last_updated_date TIMESTAMP WITHOUT TIME ZONE,
    deletion_timestamp TIMESTAMP WITHOUT TIME ZONE
);

CREATE SCHEMA IF NOT EXISTS kafka_sink;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

