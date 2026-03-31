# Interview environment

The purpose of this repo is to provide the following information
* Provides examples of monorepo architecture for small/medium sized project
* Demonstrate module setup to allow for parallel development between multiple developers in a single repo
* Provide example of integration tests with separate executables each defined in it's own submodule. See [IntegrationTests](IntegrationTests/README.md)
* Demonstrate basic knowledge in a variety of technologies and frameworks
* Demonstrate the usefulness of Docker Compose
* Provide a template to try new technologies

This environment does not cover the following as these are tasks that can be worked in parallel and under normal circumstances would be given to another developer to perform
* Deployment best practices
* Indexes in databases
* Optimal algorithms for some operations
* Debug Ports on test containers
* UI error handling

## Quick start

### Requirements
* Java 21 
  * older version chosen intentionally to reduce problems as this is publicly available
* Docker 
  * Development was done with orbstack
* npm
  * Required for typescript sdk builders 


The following scripts does the following
1) build jars
2) start docker compose
3) start npm locally
4) tear down the docker compose file when npm has stopped


#### MAC
This has been tested and is how this developer does quick spot checks that everything is working as intended
```shell
./run_locally.sh
```

#### PC
This has not been tested as i do not have a pc to test against.  Based on documentation the bellow should work
```shell
./run_local.bat
```


Start ui locally
```shell
cd media-player-ui;
npm install;
npm start;
```

Actions from the ui
1. Log in with the token 123 (will redirect you to the qa page)
2. Generate Users
3. log in as user (It will redirect you to the series tab)
4. press play on movie of your choice ( this will redirect you to the movie which will automatically play)
5. press stop
6. The number of views and total view time should now be visible for 


## What the application does
This is a mock application for a netflix like company.

This is a monorepo with several executables required to do the following
* Manage Users.  See [Users](libs/Users/README.md) for user structures
* Manage TvSeries/movies (Does not actually store media files as part of this demo). See [Series](libs/Series/README.md) for definitions
* Capture Metrics on viewers viewing habits (how long was a viewing session, how many episodes etc)

### Media Player Workflow
When the application plays some media it interacts with the backend system in the following manner.
![media workflow](doc/assets/media-player-workflow.jpg)

## Assumptions

* This is not intended to be replace the interview process.
* Shows how to setup a project in a way where work can be parallelized between multiple developers.

## IDE Setup

It is recommended that you set your ide to run `./gradlew clean` task before it runs `./gradlew test`.  This is because integration tests require the fat jars produced by other submodules, and an elegant way to handle that has not been setup yet. 

## Running the application

You can run the application two ways.  One with h2 as the database, and secondarily with mysql as the database.

### Run the full stack

Build jars
```shell
./gradlew clean bootJar
```

Build Images and run all applications
```shell
docker compose -f docker-compose.yml -f docker-compose.fixedport.yml -f docker-compose.stack.yml -f docker-compose.stack.fixedport.yml up -d --build 
```

Build Images and run all dependencies on fixed ports
```shell
docker compose -f docker-compose.yml -f docker-compose.fixedport.yml up -d --build 
```


NOTE:
Currently the docker files compose files are kept in two file to allow running `docker-compose up` and running locally for debugging purposes.   

Documentation on running the apps in this manner has been removed until correct profiles are created to handle local development again

## Interacting with the application

You can use an auth token of `123` for local development.

### Running an appliction via `bootrun`

To see a list of all the endpoints, while the application is running go to
[swagger](http://localhost:8081/swagger-ui/index.html)

The health of the system can be viewed through  
[healthcheck](http://localhost:8081/actuator/health)

### Public Rest Endpoint
To see a list of all the endpoints, while the application is running go to
[swagger](http://localhost:9080/swagger-ui/index.html)

The health of the system can be viewed through  
[healthcheck](http://localhost:9080/actuator/health)

In order to see how many entries have been cached
[metric cache size](http://localhost:9080/actuator/metrics/cache.size)

### Media Management Endpoint
To see a list of all the endpoints, while the application is running go to
[swagger](http://localhost:9090/swagger-ui/index.html)

The health of the system can be viewed through  
[healthcheck](http://localhost:9091/actuator/health)

In order to see how many entries have been cached
[metric cache size](http://localhost:9091/actuator/metrics/cache.size)


## Spring Boot Conventions

This application provides a collection of submodules to act as opinionated setup for connecting with items such as drivers to connect to a third party library or modules on how the database is setup.

In order to quickly connect with these opinionated pieces, you should have the following in your application at a minimum
```java
@SpringBootApplication(scanBasePackages = {"org.amoeba.example"})
public class YourApplication {

  public static void main(String[] args) {
    SpringApplication.run(YourApplication.class, args);
  }

}

```

## MYSQL

To connect to the database run the following
```shell
docker exec -it interview-mysql-1 mysql
```

some helpful commands

If you are unfamiliar with mysql the show command is helpful.
```mysql
show databases;
```

To view all series run
```mysql
select * from series.series;
```

## POSTGRES

### Connect
to connect to postgres 
```shell
docker exec -it interview-postgres-1 psql media --u user
```

### Helper Functions

#### List Tables
```postgresql
\dt
```

#### List All Users
```postgresql
select * from users.external_user;
```

#### List All Episodes
```postgresql
select * from kafka_sink.mysql_series_episode;
```

### Add kafka_sink to search path
```postgresql
SET search_path TO kafka_sink, public;
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
