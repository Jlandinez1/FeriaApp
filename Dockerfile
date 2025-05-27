# Usa una imagen con Java 21 - Puedes usar Eclipse Temurin
FROM eclipse-temurin:21-jdk-slim as build

COPY . .
RUN mvn clean package -DskipTest

FROM eclipse-temurin:21-jdk-slim as build
COPY --from=build /target/*.jar demo.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "demo.jar"]