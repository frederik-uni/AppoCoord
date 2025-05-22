#!/bin/bash

set -e

LOG_FILE="/data/redis-healthcheck.log"

if [ "$SSL" = "true" ]; then
  CMD="redis-cli --tls --cacert /etc/redis/tls/ca.crt"
else
  CMD="redis-cli"
fi

if [ -n "$REDIS_PASSWORD" ]; then
  CMD="$CMD -a $REDIS_PASSWORD"
fi

eval $CMD ping >> "$LOG_FILE" 2>&1
EXIT_CODE=$?

echo "Exit code: $EXIT_CODE" >> "$LOG_FILE"

exit $EXIT_CODE

