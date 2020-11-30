# Spring Boot & Angular showcase

Check a live demo running at https://courses365.herokuapp.com/ 
 * Log in credentials: admin  / admin

# Project notes  

Running client locally: `ng serve`

Running server locally: `mvn spring-boot:run`

Building the client: `npm run build` (builds Angular client with parameter`environment=production` and copies it to /resources/static) 

Building the server: `mvn package`

Building docker image with Spring: `mvn spring-boot:build-image` 

Running the docker image locally: `docker run -it -p8080:8080 naglm/courses:0.0.3-SNAPSHOT` (replace the current version)

Running all via docker-compose: `docker-compose up`

Deploying to Heroku: `git push heroku master`

Deploying to Azure: `mvn package azure-webapp:deploy` (currently not working due to Azure quotas...)

Currently running on Heroku here: https://courses365.herokuapp.com/

