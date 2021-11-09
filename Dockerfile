FROM openjdk:11-jre-slim
LABEL maintainer="mymail@gmail.com"

ENV JAVA_OPTS " -Xms512m -Xmx512m "

COPY target/app-1.0.0.jar  /demo.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.jar"]
