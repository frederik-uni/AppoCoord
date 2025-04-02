FROM alpine:latest

RUN apk add --no-cache wget

RUN wget -O /etc/apk/keys/amazoncorretto.rsa.pub  https://apk.corretto.aws/amazoncorretto.rsa.pub
RUN echo "https://apk.corretto.aws/" >> /etc/apk/repositories
RUN apk update
RUN apk add amazon-corretto-24

RUN java --version

WORKDIR /app

CMD ["sh"]
