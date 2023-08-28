# Interview environment

The purpose of this repo is to provide the following information
* Provide example of documentation expectations
* Demonstrate basic knowledge in a variety of technologies and frameworks
* Demonstrate the ability to combine several technologies into a single deployable package
* Demonstrate the usefullness of Docker Compose
* Provide a template to try new technologies

## What the application does
Maintain records of the tv watch histories of individuals. 

## Assumptions

* This is not intended to be replace the interview process.
* The task is to research if cassandra should replace mysql for optimization purposes.  The application is setup in such a way where development can go forward without interfeering with tasks by other developers.

## Requirements
Docker compose needs to be installed on your machine. See [their site](https://docs.docker.com/compose/) for more information on what docker is and how to install

You have jdk 17+ installed

## Running the application

To run the application using your local machine first run

`docker-compose up`

This will instantation a local instance of databases and other technologies on your machine which the application run against.

Once Docker Compose is up and running run
`./gradlew clean build` in your terminal

## Interacting with the application

To see a list of all the endpoints, while the application is running go to
http://localhost:8080/swagger-ui/index.html

The health of the endpoint can be viewed through http://localhost:8080/actuator/health

## MYSQL

To connect to the database run the following
```
docker exec -it mysql mysql --user=root --password=rootIsABadPassword
```

some helpful commands
```
show databases;
```


## Kafka

Creating a topic

