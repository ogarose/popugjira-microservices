FROM openjdk:11.0.6-jdk-slim

VOLUME /tmp
COPY ./build/libs/authServer-0.0.1-SNAPSHOT.jar  app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
