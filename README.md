# Interview environment

The purpose of this repo is to provide the following information
* Provide example of documentation expectations
* Provides examples of monorepo architecture for small/medium sized project
* Demonstrate module setup to allow for parallel development between multiple developers in a single repo
* Provide example of integration tests with separate executables each defined in it's own submodule. See [IntegrationTests](IntegrationTests/README.md)
* Demonstrate basic knowledge in a variety of technologies and frameworks
* Demonstrate the usefulness of Docker Compose
* Provide a template to try new technologies

This environment does not cover the following as these are tasks that can be worked in parallel and under normal circumstances would be given to another developer to perform
* Deployment best practices
* Indexes in mysql
* Database Migrations
* Optimal algorithms for some operations
* Authentication
* Debug Ports on test containers

Time permitting the above should be addressed.

## Quick start

Requirements
* Java 21 
  * older version chosen intentionally to reduce problems as this is publicly avaialable
* Docker 
  * Development was done with orbstack
* npm 

Build jars
```shell
./gradlew clean build bootJar
```

Build Images and run all applications
```shell
docker compose -f docker-compose.yml -f docker-compose.fixedport.yml -f docker-compose.stack.yml -f docker-compose.stack.fixedport.yml up -d --build 
```

Start ui locally
```shell
cd media-player-ui;
npm install;
npm start;
```

Actions from the ui
1. Log in with the token 123
2. Generate Users
3. Generate Movies
4. go to users tab
5. log in as user
6. Go to series tab
7. press play on movie
8. press stop

At this point you can run from the postgres container

```postgresql
select eu.email as email, mp_start."mediaId" as media_id, (mp_stop."mediaEvent_mediaTimestampMs" - mp_start."mediaEvent_mediaTimestampMs") as view_time_ms
from kafka_sink.media_player_start mp_start
         left join kafka_sink.media_player_stop mp_stop on mp_start."eventId" = mp_stop."lastActionId"
         left join users.external_user eu on eu.public_id = uuid(mp_start."mediaEvent_userId");
```

And you will get output like

| email | media\_id | view\_time\_ms |
| :--- | :--- | :--- |
| herman.koelpin@yahoo.com | a45c871a-8b85-4476-abe2-87f0dda4b16b | 5160 |
| herman.koelpin@yahoo.com | a45c871a-8b85-4476-abe2-87f0dda4b16b | 4302 |
| herman.koelpin@yahoo.com | a45c871a-8b85-4476-abe2-87f0dda4b16b | 6851 |


* `email` is the email of the user you loged in as.
* `media_id` is the id of the media being played.  Not the series.  You will see id mismatch here as in a real world scenerio, for a movie, you may have a different media id based on locale.  I.e. spanish media id could be different from an english media id.  The media id in this case is the public id of episode in mysql
* `view_time_ms` a discreate unit of view time for a play/stop cycle



## What the application does
This is a mock application for a netflix like company.

This is a monorepo with several executables required to do the following
* Manage Users.  See [Users](Users/README.md)
* Manage TvSeries/movies (Does not actually store media files as part of this demo). See [Series](Series/README.md) for definitions
* Capture Metrics on viewers viewing habits (how long was a viewing session, how many episodes etc)

## Assumptions

* This is not intended to be replace the interview process.
* Shows how to setup a project in a way where work can be parallelized between multiple developers.

## Requirements
You have jdk 21+ installed

## Optional
Docker compose needs to be installed on your machine for local integration tests. See [their site](https://docs.docker.com/compose/) for more information on what docker is and how to install

In otherwords, if running against a local mysql, cassandra, or other database/tool, you will need the above installed

## IDE Setup

It is recomended that you set your ide to run `./gradlew clean` task before it runs `./gradlew test`.  This is because integration tests require the fat jars produced by other submodules, and an elegant way to handle that has not been setup yet. 

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
docker exec -it interview-postgres-1 psql testdb --u user
```

### Helper Functions

#### List Tables
```postgresql
\dt
```

#### List All Users
```postgresql
select * from external_user;
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
