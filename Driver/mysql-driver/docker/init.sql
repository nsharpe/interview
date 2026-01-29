USE testdb;

CREATE DATABASE series;

GRANT ALL PRIVILEGES ON series.* TO 'user'@'%';

FLUSH PRIVILEGES;

use series;

CREATE TABLE series
(
    id                 BIGINT AUTO_INCREMENT   NOT NULL,
    public_id          BINARY(16)              NOT NULL,
    title              VARCHAR(255)            NULL,
    locale             VARCHAR(255)            NOT NULL,
    `description`      VARCHAR(5000)           NULL,
    creation_timestamp timestamp DEFAULT NOW() NULL,
    last_updated_date  datetime                NULL,
    deletion_timestamp datetime                NULL,
    CONSTRAINT pk_series PRIMARY KEY (id)
);

ALTER TABLE series
    ADD CONSTRAINT uc_series_public UNIQUE (public_id);

ALTER TABLE series
    ADD CONSTRAINT uc_series_title UNIQUE (title);

CREATE TABLE season
(
    id                 BIGINT AUTO_INCREMENT   NOT NULL,
    public_id          BINARY(16)              NOT NULL,
    title              VARCHAR(255)            NOT NULL,
    season_order       INT                     NOT NULL,
    series_id          BIGINT                  NOT NULL,
    creation_timestamp timestamp DEFAULT NOW() NULL,
    last_updated_date  datetime                NULL,
    deletion_timestamp datetime                NULL,
    CONSTRAINT pk_season PRIMARY KEY (id)
);

ALTER TABLE season
    ADD CONSTRAINT uc_season_public UNIQUE (public_id);

ALTER TABLE season
    ADD CONSTRAINT FK_SEASON_ON_SERIES FOREIGN KEY (series_id) REFERENCES series (id);

CREATE TABLE episode
(
    id                 BIGINT AUTO_INCREMENT   NOT NULL,
    public_id          BINARY(16)              NOT NULL,
    title              VARCHAR(255)            NOT NULL,
    length             BIGINT                  NULL,
    episode_order      INT                     NOT NULL,
    season_id          BIGINT                  NULL,
    creation_timestamp timestamp DEFAULT NOW() NULL,
    last_updated_date  datetime                NULL,
    deletion_timestamp datetime                NULL,
    CONSTRAINT pk_episode PRIMARY KEY (id)
);

ALTER TABLE episode
    ADD CONSTRAINT uc_episode_public UNIQUE (public_id);

ALTER TABLE episode
    ADD CONSTRAINT FK_EPISODE_ON_SEASON FOREIGN KEY (season_id) REFERENCES season (id);