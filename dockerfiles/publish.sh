#!/bin/bash

USERNAME="frederikuni"

docker build -t $USERNAME/amazoncorretto24-alpine:latest -f java/Dockerfile .
docker push $USERNAME/amazoncorretto24-alpine:latest

docker build -t $USERNAME/pnpm-alpine:latest -f pnpm/Dockerfile .
docker push $USERNAME/pnpm-alpine:latest

docker build -t $USERNAME/maven-amazoncorretto24-alpine:latest -f maven/Dockerfile .
docker push $USERNAME/maven-amazoncorretto24-alpine:latest

docker build -t $USERNAME/nginx-alpine:latest -f nginx/Dockerfile .
docker push $USERNAME/nginx-alpine:latest