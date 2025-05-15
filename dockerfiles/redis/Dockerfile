FROM alpine:latest

RUN apk add --no-cache redis

COPY redis/config.conf /etc/redis/redis.conf
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

EXPOSE 6379

WORKDIR /data
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]

CMD ["redis-server", "/etc/redis/redis.conf"]
