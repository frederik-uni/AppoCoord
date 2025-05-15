#!/bin/sh
set -e

CMD_ARGS="/etc/redis/redis.conf"

if [ "$SSL" = "true" ]; then
  CMD_ARGS="--tls-port 6379 --port 0 \
  --tls-cert-file /etc/redis/tls/redis.crt \
  --tls-key-file /etc/redis/tls/redis.key \
  --tls-ca-cert-file /etc/redis/tls/ca.crt"
else
  CMD_ARGS="/etc/redis/redis.conf"
fi

if [ -n "$API_KEY" ]; then
  CMD_ARGS="$CMD_ARGS --requirepass $API_KEY"
fi

exec redis-server $CMD_ARGS