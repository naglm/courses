# Spring Boot & Angular showcase app

Check out  live demo running at https://courses365.herokuapp.com/ 
 * You may need to wait a while until Heroku free dyno goes up  
 * Log in credentials: admin  / admin

# Project structure
It's a Maven project with the following structure: 
```
courses     - parent project
└──backend  - Spring Boot backend
└──frontend - Angular frontend
 ```  

# Local development
* Running frontend: `cd frontend` and then `ng serve`. Or run it from your IDE which detects it :)
* Running server: `cd backend` and then `mvn spring-boot:run`. Or run it from your IDE which detects it :)

# Building
* Building an executable JAR with frontend & backend all-in-one: `mvn package`  
* Building docker image with Spring: `mvn spring-boot:build-image` 
* Running the docker image locally: `docker run -it -p8080:8080 naglm/courses:0.0.3-SNAPSHOT` (replace the current version)
* Running all via docker-compose: `docker-compose up`

# Deploying 
* Deploying to Heroku: `heroku deploy:jar backend/target/*.jar --app courses365`
