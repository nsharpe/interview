# CLAUDE.md — MediaPlayer Dev Rules

## Build & Run
- **Build All:** `./gradlew clean bootJar`
- **Infrastructure:** `docker compose -f docker-compose.yml -f docker-compose.fixedport.yml -f docker-compose.stack.yml -f docker-compose.stack.fixedport.yml up -d --build`
- **UI:** `./gradlew :media-player-ui:npmStart`
- **Tests:** `./gradlew :integration-tests:test` (All integration tests live here)
- **Auth:** Use token `123` for local dev.

## Project Architecture (Composite Build)
- **Root:** All `./gradlew` commands must run from the root.
- **Plugins:** Defined in `gradle-plugins/`. Always check `gradle-plugins/src/main/kotlin/*` for build logic.
- **Convention:** Apps use `@SpringBootApplication(scanBasePackages = {"org.amoeba.example"})`.
- **Topics:** Kafka topics are centralized in `drivers/kafka-driver/.../KafkaTopics.java`.

## Critical Pathing (Read this before searching)
- **Never** search from `/`.
- **DBs:** MySQL (3306, Series/Episodes), Postgres (5432, Users), Redis (6379, Auth).
- **External Docs:** Reference `../spring-boot` and `../spring-data-jpa` for documentation.

## Module Map
- `apps/`: Main entry points (Admin: 8081, Public: 9080, Media: 9090).
- `drivers/`: JPA/Configurations (MySQL, Postgres, Redis, Kafka).
- `util/`: `java-core` (Plain Java), `spring-util` (Spring beans).
- `libs/`: Domain logic (`avro-model`, `series`, `users`).