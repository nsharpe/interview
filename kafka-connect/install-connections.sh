#!/usr/bin/env bash

while ! nc -z localhost 8083; do
               echo 'Waiting for kafka connect...';
               sleep 1;
             done;
             echo 'kafka connect is up';

until [ "$(curl -s -o /dev/null -w "%{http_code}" localhost:8083/connectors)" = "200" ]; do sleep 1; done;
echo "connectors init"
echo "Install Connections";

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d @./connections/postgres-sink.json
