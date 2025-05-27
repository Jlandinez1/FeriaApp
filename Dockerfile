# Etapa de construcción con Maven + Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY . .
RUN mvn clean package -DskipTests

# Etapa final – Ejecutar la app
FROM eclipse-temurin:21.0.6_7-jre
WORKDIR /app
COPY --from=build target/feriaApp.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]