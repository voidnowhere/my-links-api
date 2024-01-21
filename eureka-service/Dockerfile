FROM maven:3.8-openjdk-17-slim as build

WORKDIR /app

COPY . /app

RUN mvn clean package

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/eureka-service.jar /app

ENTRYPOINT ["java", "-jar", "eureka-service.jar"]
