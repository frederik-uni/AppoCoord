#!/bin/bash

mkdir certs
mkdir certs/https
mkdir certs/redis-tls
openssl req -x509 -nodes -days 365 \
  -newkey rsa:2048 \
  -keyout https/privkey.pem \
  -out https/fullchain.pem \
  -subj "/CN=localhost"