

CREATE TABLE IF NOT EXISTS kafka_sink.mysql_series_series (
  id BIGINT NOT NULL PRIMARY KEY,
  public_id BYTEA NOT NULL,
  title TEXT NOT NULL,
  locale TEXT NOT NULL,
  description TEXT,
  series_type TEXT NOT NULL,
  creation_timestamp TEXT,
  last_updated_date BIGINT,
  deletion_timestamp BIGINT,
  __deleted TEXT
);

CREATE TABLE IF NOT EXISTS kafka_sink.mysql_series_season (
  id BIGINT NOT NULL PRIMARY KEY,
  public_id BYTEA NOT NULL,
  title TEXT NOT NULL,
  season_order INTEGER NOT NULL,
  series_id BIGINT NOT NULL,
  creation_timestamp TEXT,
  last_updated_date BIGINT,
  deletion_timestamp BIGINT,
  __deleted TEXT
);

CREATE TABLE IF NOT EXISTS kafka_sink.mysql_series_episode (
   id BIGINT NOT NULL PRIMARY KEY,
   public_id BYTEA NOT NULL,
   title TEXT NOT NULL,
   length BIGINT,
   episode_order INTEGER NOT NULL,
   season_id BIGINT,
   creation_timestamp TEXT,
   last_updated_date BIGINT,
   deletion_timestamp BIGINT,
   __deleted TEXT
);

CREATE TABLE IF NOT EXISTS kafka_sink.media_player_start (
    "mediaId" TEXT NOT NULL,
    "mediaEvent_userId" TEXT NOT NULL,
    "mediaEvent_mediaTimestampMs" BIGINT NOT NULL,
    "mediaEvent_timestamp" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "lastActionId" TEXT,
    "eventId" TEXT NOT NULL,
    PRIMARY KEY ("eventId")
);

CREATE TABLE IF NOT EXISTS kafka_sink.media_player_stop (
    "mediaEvent_userId" TEXT NOT NULL,
    "mediaEvent_mediaTimestampMs" BIGINT NOT NULL,
    "mediaEvent_timestamp" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "lastActionId" TEXT NOT NULL,
    "eventId" TEXT NOT NULL,
    PRIMARY KEY ("eventId")
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA kafka_sink TO kafka_connect_user;