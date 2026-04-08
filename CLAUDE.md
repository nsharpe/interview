# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Quick Start

**Requirements:** Java 21, Docker, npm

**Build and run:**
```shell
./gradlew clean bootJar
docker compose -f docker-compose.yml -f docker-compose.fixedport.yml -f docker-compose.stack.yml -f docker-compose.stack.fixedport.yml up -d --build
```

**React UI (local):**
```shell
./gradlew :media-player-ui:npmStart
```

**Auth token:** `123` (for local development)

Never search from the path `/`.



## Sub-Agent Routing Rules
 
**Parallel dispatch** (ALL conditions must be met):
 
- 3+ unrelated tasks or independent domains
- No shared state between tasks
- Clear file boundaries with no overlap
 
**Sequential dispatch** (ANY condition triggers):
 
- Tasks have dependencies (B needs output from A)
- Shared files or state (merge conflict risk)
- Unclear scope (need to understand before proceeding)
 
**Background dispatch**:
 
- Research or analysis tasks (not file modifications)
- Results aren't blocking your current work

## Documentation Search
If you are looking for spring documentation you can try the following directories

** Spring boot **
```
../spring-boot
```

** Spring Jpa
```
../spring-data-jpa

## Gradle

For

**Gradle commands**
all gradle commands should be run from the root of the project

for example building the mysql driver should be done as 
```
./gradlew :drivers:mysql-driver:build
```

```
### Gradle Build Types

This project uses two types of Gradle build structures:

#### Composite Builds
These are top-level modules that include other builds in the composite:
- `root` (MediaPlayer) - Main entry point
- `drivers/` - Database drivers module (includes mysql-driver, postgres-driver, etc.)
- `libs/` - Domain-specific modules
- `media-management/` - Media CRUD endpoints
- `user-management-root/` - Public API endpoints
- And others...

These are included using `includeBuild("path")` in settings.gradle.kts files.

#### Regular Submodules
These are individual projects that exist within composite builds:
- `drivers/mysql-driver/`
- `drivers/postgres-driver/`
- `drivers/kafka-driver/`
- `drivers/redis-driver/`

These are included using `include(":project-name")` in their parent's settings.gradle.kts.

### Key Distinction:
- **Composite builds** (`includeBuild`) - These can be built independently and include other builds
- **Regular submodules** (`include`) - These are projects within a composite build that depend on each other

### Gradle Plugins
Gradle plugins allow centralizing common build tasks into a central location.  They are located in the `gradle-plugins` submodule.

For example, common build tasks for all java applications are in the file `gradle-plugins/src/main/kotlin/java-convention.gradle.kts`

Plugins are included by having 
```kotlin
pluginManagement {
   includeBuild(<relative path to the gradle-plugin submodule>)
}
```
in the `settings.gradle.kts` of the module.

You can include them in a `build.gradle.kts` (using `java-convention` as an example) with
```kotlin
plugins {
   `java-convention`
}
```




## Module Structure and Dependencies

In this project, modules can be either:
1. **Top-level composite builds** - These include their own subprojects and other builds
2. **Submodules within composite builds** - These are regular projects that exist within a build but don't form their own composite

For example, in the `drivers/` module:
drivers/
├── settings.gradle.kts          # Includes mysql-driver, postgres-driver, kafka-driver, redis-driver
├── mysql-driver/                # Regular submodule - no includeBuild(), only include()
├── postgres-driver/             # Regular submodule
└── ...

The `drivers/settings.gradle.kts` includes all its submodules:
  ```kotlin
  include("mysql-driver")
  include("postgres-driver")
  include("kafka-driver")
  include("redis-driver")
```

  But it also includes parent builds for dependency resolution:
```kotlin
  includeBuild("../gradle-plugins")
  includeBuild("../avro-model")
```

## Understanding the Build Process

When you run Gradle commands:

1. **Root-level commands** (e.g., `./gradlew :drivers:mysql-driver:build`):
    - Gradle resolves the composite build structure
    - It finds `drivers/` as a composite build and follows its settings.gradle.kts
    - It then finds `mysql-driver` as an included project within that build

2. **Submodule-specific commands**:
    - For modules like `mysql-driver`, you can run `./gradlew :drivers:mysql-driver:build`
    - The `mysql-driver/build.gradle.kts` is processed directly, not through a composite structure

This hybrid approach allows for both modular development and efficient dependency resolution across the entire codebase.

## Dependency Resolution Clarification

When looking for dependencies:

- **Local dependencies**: Projects within the same composite build are resolved directly, not as packaged jars
- **External dependencies**: Resolved from Maven repositories (like `com.mysql:mysql-connector-j`)
- **Build process**: When `bootJar` is run, Gradle packages all necessary dependencies into executable JARs

This means:
1. When you reference `org.amoeba.example.drivers:mysql-driver` in a build.gradle.kts, it resolves to the local project
2. When you reference external libraries, they are resolved from Maven repositories

## Architecture

This is a **monorepo** with Spring Boot Gradle composite build, organized by domain:

```
├── kafka-connect          - All configuration/shell scripts for running kafka connect
├── apps/
    ├── admin-endpoint/        - Admin API for user management
    ├── user-management-root/  - User management tools API + SDKs
    ├── media-metric-endpoint/ - provides endpoints for users to get information about the performance of a piece of media
├── libs/
    ├── avro-model         - All avro models are stored here, all kafka streams should use avro for key and records
    ├── series/            - TV series/movies domain (MySQL)
    └── users/             - User domain (PostgreSQL)
├── util/                  - Utility packages
    └── java-core/         - Shared java models and utilities that don't require spring
├── drivers/               - Database drivers (MySQL, PostgreSQL, Redis, Kafka), and flyway migration scripts
    ├── kafka-driver/      - Kafka helper functions, configurations, and topic locations
    ├── mysql-driver/      - Embeded jpa classes for mysql, along with mysql helpers and configurstion
    ├── postgres-driver/   - Embeded jpa classes for postgres, along with postgres helpers and configurations
    ├── redis-driver/      - Everything needed for redis
├── spring-util/           - Provides a variety of spring specific utilities
    └── spring-rest/       - Provides beans for rest controllers   
├── integration-tests/     - Integration tests (runs full docker stack)
├── media-management/       - Media CRUD endpoints + SDKs
    ├── media-management-webapp/ - The spring boot application for MediaManagement
├── test-data/              - Test data generators.  This creates random users, movies etc
├── media-player-endpoint-root/ - Media player event tracking
├── migration-scripts/      -  Holds all database migration scripts.  These scripts can be used to get information about the database
└── qa-endpoint-root/      - QA/generator endpoints
```

Information about dependencies for a module are stored in `settings.gradle.kts` for that module

## Key Patterns

- **Spring Boot 3.5.10** with Gradle Kotlin DSL
- **OpenAPI 2.0** generated from controllers via springdoc-openapi
- **Docker Compose** manages: Kafka, MySQL, PostgreSQL, Redis, Schema Registry, Kafka Connect
- **TestContainers-style** integration tests using docker-compose profiles
- **Kafka Connect** used to transfer information between datasources

## Spring Boot Paterns

All applications do the following
```
@SpringBootApplication(scanBasePackages = {"org.amoeba.example"})  
```
so that libraries that are defined in other modules are automatically imported

## Kafka

Kafka topics produced or read by java applications are stored in `drivers/kafka-driver/src/main/java/org/example/kafka/KafkaTopics.java`.

## Build Tasks

| Task | Purpose |
|------|---------|
| `./gradlew clean` | Clean all subprojects |
| `./gradlew build` | Build all projects (requires bootJar for IntTests) |
| `./gradlew bootJar` | Create executable JARs |
| `./gradlew bootRun` | Run a Spring Boot app (uses docker-compose.yml) |
| `./gradlew test` | Run tests (disabled at root - run in subproject) |

## TypeScript SDKs

Generated from OpenAPI specs. Build flow:
1. `openApi` task generates spec
2. `openApiGenerate` creates SDK source
3. `npm install` + `npm build` compile TypeScript
4. `npm link` enables local consumption

Subprojects with TS SDKs:
- `admin-typescript-sdk`
- `user-management-endpoint-typescript-sdk`
- `media-management-typescript-sdk`
- `media-player-typescript-sdk`
- `qa-endpoint-typescript-sdk`

## Database

| Service | Port | DB | Use |
|---------|------|-----|-----|
| MySQL | 3306 | testdb | Series, Season, Episode (Spring Data JPA) |
| PostgreSQL | 5432 | media | Users, Kafka sink (JDBC) |
| Redis | 6379 | - | Authentication cache |

## API Documentation

| Service | Swagger | Health                 |
|---------|---------|------------------------|
| Admin | http://localhost:8081/swagger-ui | /admin/actuator/health |
| Public Rest | http://localhost:9080/swagger-ui | /actuator/health       |
| Media Management | http://localhost:9090/swagger-ui | /actuator/health       |
| Media Player | http://localhost:9091/swagger-ui | /actuator/health       |
| QA | http://localhost:8085/swagger-ui | /actuator/health       |

## Integration Tests

All integration tests are in the `integration-tests` submodule

To run integration tests run 
```shell
./gradlew :integration-tests:test
```
Test containers use `TestContainers.java` profile with fixed ports for predictable testing.
