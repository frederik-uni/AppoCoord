FROM local-pnpm-alpine AS frontend-build
WORKDIR /frontend
COPY ./ ./
RUN rm -rf node_modules
RUN pnpm install  --shamefully-hoist --no-frozen-lockfile
RUN pnpm run preview