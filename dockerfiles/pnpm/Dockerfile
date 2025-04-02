FROM alpine:latest

RUN apk add --no-cache \
    nodejs \
    npm \
    git

RUN npm install -g pnpm

RUN node -v && npm -v && pnpm -v

WORKDIR /app

CMD ["sh"]
