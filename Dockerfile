FROM maven:3.9.9-ibm-semeru-17-focal AS build
COPY . .
RUN mvn clean package -DskipTest

FROM ibm-semeru-runtimes:open-17-jre 
COPY --from=build /target/*.jar demo.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "demo.jar"]