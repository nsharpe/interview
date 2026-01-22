FROM eclipse-temurin:21-jre-alpine

ARG APP

WORKDIR /app
COPY ${APP}/build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]