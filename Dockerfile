# Usa una imagen con Java 21 - Puedes usar Eclipse Temurin
FROM eclipse-temurin:21-jdk-slim as build

# Copia código y paquete pom.xml
COPY . .
RUN mvn clean package -DskipTests

# Imagen final (puedes usar JRE si prefieres)
FROM eclipse-temurin:21-jre-slim
WORKDIR /app
COPY --from=build target/feriaApp.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]