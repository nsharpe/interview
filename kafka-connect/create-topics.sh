#!/usr/bin/env bash

kafka-topics --bootstrap-server broker:29092 \
        --create --if-not-exists \
        --topic schema-changes.series \
        --partitions 1 \
        --replication-factor 1 \
        --config cleanup.policy=delete


BOOTSTRAP="broker:29092"
PREFIX="mysql_series.series"
TABLES=("episode" "season" "series")
PARTITIONS=3
REPLICATION=1 # Increase to 3 for production clusters

echo "Creating topics for Debezium MySQL connector..."

for TABLE in "${TABLES[@]}"
do
    TOPIC_NAME="$PREFIX.$TABLE"
    echo "Creating topic: $TOPIC_NAME"

    kafka-topics --bootstrap-server $BOOTSTRAP --create \
        --if-not-exists \
        --topic "$TOPIC_NAME" \
        --partitions $PARTITIONS \
        --replication-factor $REPLICATION \
        --config cleanup.policy=delete \
        --config compression.type=lz4
done

BOOTSTRAP="broker:29092"
PREFIX="media.player"
TABLES=("start" "stop" "pause")
PARTITIONS=3
REPLICATION=1 # Increase to 3 for production clusters

echo "Creating topics for Debezium MySQL connector..."

for TABLE in "${TABLES[@]}"
do
    TOPIC_NAME="$PREFIX.$TABLE"
    echo "Creating topic: $TOPIC_NAME"

    kafka-topics --bootstrap-server $BOOTSTRAP --create \
        --if-not-exists \
        --topic "$TOPIC_NAME" \
        --partitions $PARTITIONS \
        --replication-factor $REPLICATION \
        --config cleanup.policy=delete \
        --config compression.type=lz4
done

