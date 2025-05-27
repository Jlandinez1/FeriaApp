# Etapa final – Ejecutar la app
FROM eclipse-temurin:21.0.6_7-jre

WORKDIR /app

COPY --from=build target/feriaApp.jar app.jar

EXPOSE 8087

CMD ["java", "-jar", "app.jar"]