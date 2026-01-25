#!/bin/sh
redis-server --daemonize yes

until redis-cli ping; do
  sleep 1
done

redis-cli SET "Bearer 123" '{"roles":["ADMIN"]}'

redis-cli shutdown

exec redis-server