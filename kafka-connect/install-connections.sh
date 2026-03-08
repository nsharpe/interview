#!/usr/bin/env bash

while ! nc -z localhost 8083; do
               echo 'Waiting for kafka connect...';
               sleep 1;
             done;
             echo 'kafka connect is up';

until [ "$(curl -s -o /dev/null -w "%{http_code}" localhost:8083/connectors)" = "200" ]; do sleep 1; done;
echo "connectors init"
echo "Install Connections";

echo "Install media player postgres Connector"
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d @./connections/postgres-media-player-sink.json

echo "Install postgres mysql topic sink"
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d @./connections/postgres-mysql-topic-sink.json

echo "Install Mysql Connector"
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d @./connections/mysql-producer.json
