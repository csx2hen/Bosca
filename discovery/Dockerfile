FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/discovery-0.0.1-SNAPSHOT.jar discovery.jar
ENTRYPOINT ["java", "-jar", "discovery.jar"]