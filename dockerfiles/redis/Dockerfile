FROM alpine:latest

RUN apk add --no-cache redis

EXPOSE 6379

WORKDIR /data

CMD ["redis-server"]
