FROM alpine:latest

RUN apk add --no-cache redis

COPY redis/config.conf /etc/redis/redis.conf

EXPOSE 6379

WORKDIR /data

CMD ["redis-server", "/etc/redis/redis.conf"]
