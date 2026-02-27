#!/usr/bin/env bash

while ! nc -z broker 29092; do
        sleep 1
done

kafka-topics --bootstrap-server broker:29092 \
        --create --if-not-exists \
        --topic schema-changes.series \
        --partitions 1 \
        --replication-factor 1 \
        --config cleanup.policy=compact
