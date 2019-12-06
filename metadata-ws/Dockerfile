FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/metadata-ws-0.0.1-SNAPSHOT.jar metadata.jar
ENTRYPOINT ["java", "-jar", "metadata.jar"]