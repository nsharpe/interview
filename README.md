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
`./gradlew clean bootRun` in your terminal

If you want to interact with the rest endpoints without connecting to a database you can run
`./gradlew clean restEndpoint:bootRun --args='--spring.profiles.active=rest_only'`

## Interacting with the application

To see a list of all the endpoints, while the application is running go to
http://localhost:8080/swagger-ui/index.html

The health of the endpoint can be viewed through http://localhost:8080/actuator/health

## Framework Documentation
This was generated via the start.spring.io process

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.3/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#web)
* [Spring Data for Apache Cassandra](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#data.nosql.cassandra)
* [Spring Data Reactive Redis](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#data.nosql.redis)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#actuator)
* [Spring cache abstraction](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#io.caching)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Spring Data for Apache Cassandra](https://spring.io/guides/gs/accessing-data-cassandra/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Caching Data with Spring](https://spring.io/guides/gs/caching/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

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

