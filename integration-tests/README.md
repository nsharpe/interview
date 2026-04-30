# Integration Tests

This section of the code allows for integration tests between separate executables contained within this repo

The tests spin up a full stack of all technologies, and application that are used by the media ecosystem.

See [TestContainers.java](src/test/java/org/amoeba/example/test/util/TestContainers.java) for how the docker composer containers are initalized.

See [test data](../test-data/README.md) for generating test data for your tests.

Also see
- [docker-compose](../docker-compose.yml)
  - Third party applications used in the ecosystem
- [docker-compose.stack](../docker-compose.stack.yml)
  - Executables that are produced by this repsotitory and any sidecars required to run as part of this application
