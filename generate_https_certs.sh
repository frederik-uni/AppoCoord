#!/bin/bash

mkdir certs
mkdir certs/https
mkdir certs/redis-tls
openssl req -x509 -nodes -days 365 \
  -newkey rsa:2048 \
  -keyout certs/https/privkey.pem \
  -out certs/https/fullchain.pem \
  -subj "/CN=localhost"