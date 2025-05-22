FROM alpine:latest

RUN apk add --no-cache wget curl bash openssl build-base openssl-dev make

RUN wget https://download.redis.io/redis-stable.tar.gz

RUN tar -xzvf redis-stable.tar.gz && \
    cd redis-stable && \
    make install BUILD_TLS=yes

COPY redis/config.conf /etc/redis/redis.conf
COPY redis/docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

RUN mkdir -p /opt/redis/utils
RUN curl -fsSL https://raw.githubusercontent.com/allen-munsch/docker-redis-ssl-example/main/redis/gen-redis-certs.sh -o /opt/redis/utils/gen-redis-certs.sh
RUN chmod +x /opt/redis/utils/gen-redis-certs.sh

EXPOSE 6379

WORKDIR /data
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]

CMD ["redis-server", "/etc/redis/redis.conf"]
