FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build target/*.jar emailsender-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "emailsender-0.0.1-SNAPSHOT.jar"]
