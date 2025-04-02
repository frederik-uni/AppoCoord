FROM local-pnpm-alpine AS frontend-build
WORKDIR /frontend
COPY frontend/ ./
RUN rm -rf node_modules
RUN pnpm install  --shamefully-hoist --no-frozen-lockfile
RUN pnpm run build

FROM local-maven-amazoncorretto24-alpine AS backend-build
WORKDIR /app

COPY pom.xml /app
COPY .mvn /app/.mvn
COPY src /app/src
COPY --from=frontend-build /frontend/dist /app/src/main/resources/static

RUN mvn clean package -DskipTests

FROM local-amazoncorretto24-alpine AS run

COPY --from=backend-build /app/target/server.jar /app/server.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/server.jar"]