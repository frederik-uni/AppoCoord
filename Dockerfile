FROM frederikuni/pnpm-alpine:latest AS frontend-build
WORKDIR /frontend
COPY frontend/ ./
RUN rm -rf node_modules
RUN pnpm install  --shamefully-hoist --no-frozen-lockfile
RUN pnpm run build

FROM frederikuni/maven-amazoncorretto24-alpine:latest AS backend-build
WORKDIR /app

COPY pom.xml /app
COPY .mvn /app/.mvn
COPY src /app/src
COPY --from=frontend-build /frontend/dist /app/src/main/resources/static

RUN mvn clean package -DskipTests

FROM frederikuni/amazoncorretto24-alpine:latest AS run

COPY --from=backend-build /app/target/server.jar /app/server.jar

ENTRYPOINT ["java", "-jar", "/app/server.jar"]