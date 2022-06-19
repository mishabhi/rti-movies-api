## Requirements:
- Docker (If you don't have docker, you can install from [here](https://docs.docker.com/get-started/#download-and-install-docker))
    
## Steps to run on local (within docker container):
    Run below set of commands to build and run the application (along with database and pgadmin) in docker
   1. Build the jar file: `./gradlew build`
   2. Run the docker compose: `docker-compose up -d`
   3. If you run `docker-compose ps` your application should be up and running

## Steps to run on local (outside docker container)
    Run below set of commands to run api outside docker
   1. Run database and pgadmin inside docker container : `docker-compose up -d rti-db rti-db-admin`
   2. Rename file .env.development to .env and set the appropriate values
   3. Run API `./gradlew bootRun --args='--spring.profiles.active=dev'`

## Build and push the docker image to docker hub
1. configure the environment variables (like AWS_ECR_URL, AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY) in repository secrets
2. Merge the feature branch or push to main branch to trigger CI

## Improvements
1. More unit test cases to cover service layer and domain layer
2. Addition of Integration tests



