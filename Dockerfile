# Etapa 1: Construcción del JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera para ejecución
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/feriaApp.jar app.jar

# Exponer el puerto de tu aplicación (ajusta si usas otro)
EXPOSE 8087

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
