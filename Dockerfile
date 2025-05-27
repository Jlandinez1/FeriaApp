# Etapa de construcción con Maven
FROM maven:4.0.0-jdk-21 AS build
WORKDIR /workspace
COPY . .
RUN mvn clean package -DskipTests

# Etapa final – Ejecutar la app
FROM eclipse-temurin:21.0.6_7-jre
WORKDIR /app
COPY --from=build target/feriaApp.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]