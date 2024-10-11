@echo off

docker network inspect my_network >nul 2>&1 || docker network create my_network

docker build -t spring-app .
docker run -d --name spring_app --network my_network -p 8080:8080 spring-app