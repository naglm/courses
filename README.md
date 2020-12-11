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
* Starting dev environment: `docker-compose up -d`
* Running frontend: `cd frontend` and then `ng serve`. Or run Angular CLI server from your IDE which detects it :)
* Running server: `cd backend` and then `mvn spring-boot:run` with params:
    * Spring profile `dev`: `--spring.profiles.active=dev`    
    * set AWS S3 access key and secret key: `--amazon.s3.access-key=<your-access-key> --amazon.s3.secret-key=<your-secret-key>`
    * The full command is: `mvn spring-boot:run --spring.profiles.active=dev --amazon.s3.access-key=<your-access-key> --amazon.s3.secret-key=<your-secret-key>`

# Building
* Building an executable JAR with frontend & backend all-in-one: `mvn package`  
* Building docker image with Spring: `cd backend` and then `mvn spring-boot:build-image` 
* Running the docker image locally: `docker run -it -p8080:8080 naglm/courses:0.0.6-SNAPSHOT` (replace the current version)
* Running all via docker-compose: `docker-compose up`

# Deploying 
* Deploying to Heroku: `heroku deploy:jar backend/target/*.jar --app courses365`
