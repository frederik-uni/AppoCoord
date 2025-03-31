FROM maven:3.9.9-amazoncorretto-24-alpine AS build
WORKDIR /app

COPY pom.xml /app
COPY .mvn /app/.mvn
COPY src /app/src

RUN mvn clean package -DskipTests

FROM amazoncorretto:24-alpine-jdk AS run

COPY --from=build /app/target/server.jar /app/server.jar
COPY application.properties /app

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/server.jar", "--spring.config.location=file:/app/application.properties"]