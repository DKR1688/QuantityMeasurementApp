FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY . .
RUN mvn -B -pl quantity-service -am clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /build/quantity-service/target/*.jar app.jar

EXPOSE 8082

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
