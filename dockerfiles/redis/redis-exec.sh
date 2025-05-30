#!/bin/bash

set -euo pipefail

LOG_FILE="/data/log/redis-command.log"
REDIS_HOST="${REDIS_HOST:-127.0.0.1}"
REDIS_PORT="${REDIS_PORT:-6379}"
SSL="${SSL:-false}"
REDIS_PASSWORD="${REDIS_PASSWORD:-}"

timestamp() {
  date +"%Y-%m-%d %H:%M:%S"
}

log() {
  echo "[$(timestamp)] $1" >> "$LOG_FILE"
}

CMD=(/usr/local/bin/redis-cli -h "$REDIS_HOST" -p "$REDIS_PORT")

if [[ "$SSL" == "true" ]]; then
  CMD+=(--tls --cacert /etc/redis/tls/ca.crt)
fi

if [[ -n "$REDIS_PASSWORD" ]]; then
  CMD+=(-a "$REDIS_PASSWORD")
fi

CMD+=("$@")

log "Executing: ${CMD[*]}"

set +e
OUTPUT=$("${CMD[@]}" 2>&1)
EXIT_CODE=$?
set -e

log "Exit code: $EXIT_CODE"
echo "$OUTPUT" >> "$LOG_FILE"

echo "$OUTPUT"

exit $EXIT_CODE