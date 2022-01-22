FROM maven:3.8.4-openjdk-11-slim AS build
ENV POSTGRES_HOST="das-postgres:5432"
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:11.0.13-jre
ENV POSTGRES_HOST="das-postgres:5432"
WORKDIR /app
COPY --from="build" /app/target/das-angular-api.jar app.jar
EXPOSE 8090
ENTRYPOINT java -jar app.jar