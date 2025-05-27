# Etapa de construcción con Maven
FROM maven:3.8.6-jdk-21 AS build
WORKDIR /workspace
COPY . .
RUN mvn clean package -DskipTests

# Etapa final – Ejecutar la app
FROM eclipse-temurin:21.0.6_7-jre
WORKDIR /app

# Usa el nombre correcto del JAR (con el número de versión)
COPY --from=build target/feriaApp-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]