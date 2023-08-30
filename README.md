# Interview environment

The purpose of this repo is to provide the following information
* Provide example of documentation expectations
* Demonstrate basic knowledge in a variety of technologies and frameworks
* Demonstrate the ability to combine several technologies into a single deployable package
* Demonstrate the usefulness of Docker Compose
* Provide a template to try new technologies

This environment does not cover
* Unit tests
* Integration Tests
* Deployment best practices

Time permiting the above should be addressed.

## What the application does
Maintain records of the tv watch histories of individuals.

## Assumptions

* This is not intended to be replace the interview process.
* The task is to research if cassandra should replace mysql for optimization purposes.  The application is setup in such a way where development can go forward without interfeering with tasks by other developers.

## Requirements
You have jdk 17+ installed

## Optional
Docker compose needs to be installed on your machine for local integration tests. See [their site](https://docs.docker.com/compose/) for more information on what docker is and how to install

In otherwords, if running against a local mysql, cassandra, or other database/tool, you will need the above installed

## Running the application

You can run the application two ways.  One with h2 as the database, and secondarily with mysql as the database.

### Run with H2

```
./gradlew clean restEndpoint:bootRun --args='--spring.profiles.active=rest_only'
```


### Run with MySql

First ensure that all optional installations are installed

To run the application using your local machine first run

```
docker-compose up
```

This will run local instance of databases and other technologies on your machine which the application run against.

Once Docker Compose is up and running run

```
./gradlew clean restEndpoint:bootRun --args='--spring.profiles.active=mysql'
```

in your terminal



## Interacting with the application

To see a list of all the endpoints, while the application is running go to
http://localhost:8080/swagger-ui/index.html

The health of the syste can be viewed through  
http://localhost:8080/actuator/health

In order to see how many entries have been cached
http://localhost:8080/actuator/metrics/cache.size


## MYSQL

To connect to the database run the following
```
docker exec -it mysql mysql --user=root --password=rootIsABadPassword
```

some helpful commands

If you are unfamiliar with mysql the show command is helpful.
```
show databases;
```

To view all users run
```agsl
select * from interview_db.external_user;
```


## Kafka
// TODO
Creating a topic

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
