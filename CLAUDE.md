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
cd media-player-ui && npm install && npm start
```

**Auth token:** `123` (for local development)

## Architecture

This is a **monorepo** with Spring Boot Gradle subprojects, organized by domain:

```
├── AdminEndpoint/          - Admin API for user management
├── BusinessDomain/
│   ├── Series/            - TV series/movies domain (MySQL)
│   └── Users/             - User domain (PostgreSQL)
├── Core/                  - Shared models and utilities
├── Driver/                - Database drivers (MySQL, PostgreSQL, Redis, Kafka)
├── IntegrationTests/      - Integration tests (runs full docker stack)
├── MediaManagement/       - Media CRUD endpoints + SDKs
├── PublicRestEndpoint/    - Public user-facing API + SDKs
├── SpringRest/            - Common Spring config (security, web)
├── TestData/              - Test data generators
├── media-player-endpoint-root/ - Media player event tracking
└── qa-endpoint-root/      - QA/generator endpoints
```

## Key Patterns

- **Spring Boot 3.5.10** with Gradle Kotlin DSL
- **OpenAPI 2.0** generated from controllers via springdoc-openapi
- **Docker Compose** manages: Kafka, MySQL, PostgreSQL, Redis, Schema Registry, Kafka Connect
- **TestContainers-style** integration tests using docker-compose profiles

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
- `public-rest-endpoint-typescript-sdk`
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

All integration tests are in the `IntegrationTests` submodule

To run integration tests run 
```shell
./gradlew :IntegrationTests:test
```
Test containers use `TestContainers.java` profile with fixed ports for predictable testing.
