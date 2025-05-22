#!/bin/sh
set -e

CERT_DIR="/etc/redis/tls"
CERT_FILE="$CERT_DIR/redis.crt"
KEY_FILE="$CERT_DIR/redis.key"
CA_CERT_FILE="$CERT_DIR/ca.crt"

if [ "$SSL" = "true" ]; then
  if [ ! -f "$CERT_FILE" ] || [ ! -f "$KEY_FILE" ] || [ ! -f "$CA_CERT_FILE" ]; then
    cd /etc/redis
    bash /opt/redis/utils/gen-redis-certs.sh
  fi

  CMD_ARGS="--tls-port 6379 --port 0 \
  --tls-cert-file $CERT_FILE \
  --tls-key-file $KEY_FILE \
  --tls-ca-cert-file $CA_CERT_FILE --tls-auth-clients no --bind 0.0.0.0"
else
  CMD_ARGS="--bind 0.0.0.0"
fi

if [ -n "$REDIS_PASSWORD" ]; then
  CMD_ARGS="$CMD_ARGS --requirepass $REDIS_PASSWORD"
fi

CMD_ARGS="$CMD_ARGS --protected-mode no"


exec redis-server $CMD_ARGS