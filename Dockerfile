FROM openjdk:8-alpine
MAINTAINER z.aoran@gmail.com
VOLUME /tmp
EXPOSE 8443
ARG JAR_FILE=build/libs/ezrent-back-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]