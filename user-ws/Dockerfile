FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/user-ws-0.0.1-SNAPSHOT.jar user.jar
ENTRYPOINT ["java", "-jar", "user.jar"]