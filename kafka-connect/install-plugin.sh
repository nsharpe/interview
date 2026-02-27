#!/usr/bin/env bash

echo "Install Plugins";

if [ ! -d /usr/share/confluent-hub-components/debezium-debezium-connector-postgresql ]; then
  echo "debezium-debezium-connector-postgresql";
  confluent-hub install --no-prompt debezium/debezium-connector-postgresql:3.1.2;
fi

if [ ! -d /usr/share/confluent-hub-components/confluentinc-kafka-connect-jdbc ]; then
  echo "Installing confluentinc-kafka-connect-jdbc";
  confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.9.0;
fi

if [ ! -d /usr/share/confluent-hub-components/debezium-connector-mysql ]; then
  echo "Installing mysql debezium connector";
  confluent-hub install --no-prompt debezium/debezium-connector-mysql:3.1.2;
fi