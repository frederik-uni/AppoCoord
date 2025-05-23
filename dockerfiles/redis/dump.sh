#!/bin/bash

set -euo pipefail

BACKUP_DIR="/data/backups"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
DUMP_FILE="/data/dump.rdb"
BACKUP_FILE="$BACKUP_DIR/dump_$TIMESTAMP.rdb"

mkdir -p "$BACKUP_DIR"

LASTSAVE_BEFORE=$(/redis-exec.sh LASTSAVE | tail -n1)

/redis-exec.sh BGSAVE

while true; do
  sleep 1
  LASTSAVE_NOW=$(/redis-exec.sh LASTSAVE | tail -n1)

  if [[ "$LASTSAVE_NOW" -gt "$LASTSAVE_BEFORE" ]]; then
    break
  fi
done

cp "$DUMP_FILE" "$BACKUP_FILE"
