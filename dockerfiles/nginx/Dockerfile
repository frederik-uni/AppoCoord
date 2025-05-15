FROM alpine:latest

RUN apk add --no-cache openssl curl ca-certificates

RUN printf "%s%s%s%s\n" \
        "@nginx " \
        "http://nginx.org/packages/mainline/alpine/v" \
        `egrep -o '^[0-9]+\.[0-9]+' /etc/alpine-release` \
        "/main" \
        | tee -a /etc/apk/repositories

RUN curl -o /tmp/nginx_signing.rsa.pub https://nginx.org/keys/nginx_signing.rsa.pub

RUN openssl rsa -pubin -in /tmp/nginx_signing.rsa.pub -text -noout

RUN mv /tmp/nginx_signing.rsa.pub /etc/apk/keys/

RUN apk add --no-cache nginx@nginx

RUN rm /etc/nginx/nginx.conf

RUN mkdir -p /var/run /var/log/nginx

EXPOSE 80

STOPSIGNAL SIGQUIT

CMD ["nginx", "-g", "daemon off;"]