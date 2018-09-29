FROM openjdk:8-alpine

WORKDIR /app

ADD . /app

EXPOSE 8080

CMD /app/gradlew bootRun