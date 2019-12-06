FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/file-ws-0.0.1-SNAPSHOT.jar file.jar
ENTRYPOINT ["java", "-jar", "file.jar"]