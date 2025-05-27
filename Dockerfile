# Etapa de construcción con Maven
FROM eclipse-temurin:21-jdk-slim as build
WORKDIR /workspace
COPY . .
RUN mvn clean package -DskipTests

# Etapa final – Ejecutar la app
FROM eclipse-temurin:21-jre-slim
WORKDIR /app
COPY --from=build target/feriaApp.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]