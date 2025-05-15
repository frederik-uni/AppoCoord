FROM local-pnpm-alpine
WORKDIR /frontend
COPY ./ ./
RUN rm -rf node_modules
RUN pnpm install  --shamefully-hoist --no-frozen-lockfile

EXPOSE 3000

CMD ["pnpm" ,"run", "preview"]
