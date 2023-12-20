FROM openjdk:17-jdk-slim
COPY build/libs/cafe-0.0.1-SNAPSHOT.jar cafe.jar
EXPOSE 8080
ENTRYPOINT exec java -jar cafe.jar