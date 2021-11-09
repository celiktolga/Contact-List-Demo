# Backend applicant test

## Task Description

This project contains  The project is based on a small web service which uses the following technologies:

* Java 11
* Spring Boot
* Database H2 (In-Memory)
* Maven
* Docker
```
docker build -f Dockerfile -t demo:latest .
docker run -d demo
docker images
docker run -d -p 8080:8080 --name demo-service demo:latest
docker ps
```
----
http://localhost:8080/swagger-ui/index.html
