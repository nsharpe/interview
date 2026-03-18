
-- Create views in users schema with simplified names for kafka_sink tables

CREATE OR REPLACE VIEW users.series AS
    SELECT
        id,
        public_id,
        title,
        locale,
        description,
        series_type,
        creation_timestamp,
        last_updated_date,
        deletion_timestamp,
        __deleted
    FROM kafka_sink.mysql_series_series;

CREATE OR REPLACE VIEW users.season AS
    SELECT
        id,
        public_id,
        title,
        season_order,
        series_id,
        creation_timestamp,
        last_updated_date,
        deletion_timestamp,
        __deleted
    FROM kafka_sink.mysql_series_season;

CREATE OR REPLACE VIEW users.episode AS
    SELECT
        id,
        public_id,
        title,
        length,
        episode_order,
        season_id,
        creation_timestamp,
        last_updated_date,
        deletion_timestamp,
        __deleted
    FROM kafka_sink.mysql_series_episode;

CREATE OR REPLACE VIEW users.media_player_start AS
    SELECT
        "mediaId" AS media_id,
        "mediaEvent_userId" AS user_id,
        "mediaEvent_mediaTimestampMs" AS media_timestamp_ms,
        "mediaEvent_timestamp" AS timestamp,
        "lastActionId" AS last_action_id,
        "eventId" AS event_id
    FROM kafka_sink.media_player_start;

CREATE OR REPLACE VIEW users.media_player_stop AS
    SELECT
        "mediaEvent_userId" AS user_id,
        "mediaEvent_mediaTimestampMs" AS media_timestamp_ms,
        "mediaEvent_timestamp" AS timestamp,
        "lastActionId" AS last_action_id,
        "eventId" AS event_id
    FROM kafka_sink.media_player_stop;
