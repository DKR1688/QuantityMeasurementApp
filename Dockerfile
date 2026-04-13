FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests clean package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod
ENV APP_JAR=/app/app.jar

COPY --from=builder /app/target/quantity_measurement_app-*.jar /app/app.jar
COPY scripts/run-prod.sh /app/scripts/run-prod.sh

RUN chmod +x /app/scripts/run-prod.sh

EXPOSE 8080
CMD ["bash", "/app/scripts/run-prod.sh"]
