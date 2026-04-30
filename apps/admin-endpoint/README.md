# Admin Endpoint

Endpoints for administration tasks that should not be available to the public.

The administration tasks in a real world application would be things like 

* Replaying topics
* User Moderation
* Health aggregations

### Accessing the application via `run_locally.sh`

To see a list of all the endpoints, while the application is running go to
[swagger](http://localhost:9110/admin/swagger-ui/index.html)

**NOTE** For some reason the health check endpoint is not working.  This is the only endpoint that does not have a working healthcheck

The health of the system can be viewed through
[healthcheck](http://localhost:9111/admin/actuator/health)
