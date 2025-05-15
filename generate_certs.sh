#!/bin/bash

mkdir -p redis-tls

# Generate CA key and cert
openssl genrsa -out redis-tls/ca.key 4096
openssl req -x509 -new -nodes -key redis-tls/ca.key -sha256 -days 3650 -out redis-tls/ca.crt -subj "/CN=MyRedisCA"

# Generate server key and CSR
openssl genrsa -out redis-tls/redis.key 2048
openssl req -new -key redis-tls/redis.key -out redis-tls/redis.csr -subj "/CN=redis-server"

# Sign server cert with CA
openssl x509 -req -in redis-tls/redis.csr -CA redis-tls/ca.crt -CAkey redis-tls/ca.key -CAcreateserial -out redis-tls/redis.crt -days 365 -sha256