FROM maven:3.6.3-openjdk-17

WORKDIR /app

COPY . /app

RUN mvn clean package

ENTRYPOINT ["java", "-jar", "target/eureka-service.jar"]