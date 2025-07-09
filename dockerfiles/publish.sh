#!/bin/bash

USERNAME="frederikuni"

docker buildx create --use --name multiarch-builder 2>/dev/null || docker buildx use multiarch-builder
docker buildx inspect --bootstrap

PLATFORMS="linux/amd64,linux/arm64"

docker buildx build --platform $PLATFORMS -t $USERNAME/amazoncorretto24-alpine:latest -f java/Dockerfile --push .
docker buildx build --platform $PLATFORMS -t $USERNAME/pnpm-alpine:latest -f pnpm/Dockerfile --push .
docker buildx build --platform $PLATFORMS -t $USERNAME/maven-amazoncorretto24-alpine:latest -f maven/Dockerfile --push .
docker buildx build --platform $PLATFORMS -t $USERNAME/nginx-alpine:latest -f nginx/Dockerfile --push .