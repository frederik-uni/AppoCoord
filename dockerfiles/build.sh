#!/bin/bash
docker build -t local-amazoncorretto24-alpine -f java/Dockerfile . # --no-cache --progress=plain
docker build -t local-pnpm-alpine -f pnpm/Dockerfile .
docker build -t local-redis-alpine -f redis/Dockerfile .
docker build -t local-maven-openjdk24-alpine -f maven/Dockerfile .
