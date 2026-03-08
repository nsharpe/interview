#!/usr/bin/env bash
set -e

# PostgreSQL connection configuration from environment variables
PG_HOST="${POSTGRES_HOST:-postgres}"
PG_PORT="${POSTGRES_PORT:-5432}"
PG_DB="${POSTGRES_DB:-media}"
PG_USER="${POSTGRES_USER:-kafka_connect_user}"
PG_PASSWORD="${POSTGRES_PASSWORD:-kc_password}"

# Function to substitute environment variables in JSON config
substitute_env_vars() {
  local file="$1"
  local content
  content=$(cat "$file")

  # Replace placeholders with environment variable values
  content="${content//\$\{PG_HOST\}/$PG_HOST}"
  content="${content//\$\{PG_PORT\}/$PG_PORT}"
  content="${content//\$\{PG_DB\}/$PG_DB}"
  content="${content//\$\{PG_USER\}/$PG_USER}"
  content="${content//\$\{PG_PASSWORD\}/$PG_PASSWORD}"

  echo "$content"
}

while ! nc -z localhost 8083; do
  echo 'Waiting for kafka connect...';
  sleep 1;
done
echo 'kafka connect is up';

until [ "$(curl -s -o /dev/null -w "%{http_code}" localhost:8083/connectors)" = "200" ]; do sleep 1; done;
echo "connectors init"
echo "Install Connections";

# Read mysql-producer.json and substitute environment variables
MYSQL_HOST="${MYSQL_HOST:-mysql}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-dbz_password}"

echo "Install MySQL Connector (Debezium)"
mysql_config=$(cat ./connections/mysql-producer.json)
mysql_config="${mysql_config//\$\{MYSQL_HOST\}/$MYSQL_HOST}"
mysql_config="${mysql_config//\$\{MYSQL_PASSWORD\}/$MYSQL_PASSWORD}"
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d "$mysql_config"

echo "Install media player postgres Connector"
postgres_media_config=$(substitute_env_vars "./connections/postgres-media-player-sink.json")
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d "$postgres_media_config"

echo "Install postgres mysql topic sink"
postgres_mysql_config=$(substitute_env_vars "./connections/postgres-mysql-topic-sink.json")
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  localhost:8083/connectors/ -d "$postgres_mysql_config"
