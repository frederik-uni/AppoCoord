FROM node:23-alpine AS frontend-build
WORKDIR /frontend
RUN npm install -g pnpm
COPY frontend/ ./
RUN pnpm install  --shamefully-hoist --no-frozen-lockfile
RUN pnpm run build

FROM maven:3.9.9-amazoncorretto-24-alpine AS backend-build
WORKDIR /app

COPY pom.xml /app
COPY .mvn /app/.mvn
COPY src /app/src
COPY --from=frontend-build /frontend/dist /app/src/main/resources/static

RUN mvn clean package -DskipTests

FROM amazoncorretto:24-alpine-jdk AS run

COPY --from=backend-build /app/target/server.jar /app/server.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/server.jar"]