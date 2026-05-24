FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk

COPY --from=build /app/target/candidate-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]