# QA Endpoint Webapp

Spring Boot web application that exposes QA endpoints for testing and validating API functionality.

Depends on [test-data](../../test-data/README.md) for fixtures and [spring-web](../../spring-util/spring-web/README.md) for web conventions. Images are built as `media-player/qa-endpoints:test`.

### Accessing the application via `run_locally.sh`

To see a list of all the endpoints, while the application is running go to
[swagger](http://localhost:9120/swagger-ui/index.html)

The health of the system can be viewed through
[healthcheck](http://localhost:9121/actuator/health)
