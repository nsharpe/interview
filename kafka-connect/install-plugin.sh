#!/usr/bin/env bash

echo "Install Plugins";

if [ ! -d /usr/share/confluent-hub-components/debezium-debezium-connector-postgresql ]; then
  confluent-hub install --no-prompt debezium/debezium-connector-postgresql:3.1.2;
fi

if [ ! -d /usr/share/confluent-hub-components/confluentinc-kafka-connect-jdbc ]; then
  confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.9.0
fi
