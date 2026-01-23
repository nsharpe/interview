FROM eclipse-temurin:21-jre-alpine

ARG APP
ARG BASE_PATH

WORKDIR /app
COPY ./build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]