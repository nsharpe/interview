---
name: gradle
description: Run Gradle build, test, and run commands for the media-ecosystem composite project
when_to_use: User wants to build, test, run, or clean Gradle projects; asks about compilation, bootRun, bootJar, integration tests, or the dev UI
argument-hint: "[task] [module]"
---

# Gradle Skill

Run all commands from the repository root `/Users/someuser/git/interview` using `./gradlew`.

## Common Commands

| Action | Command                               |
|---|---------------------------------------|
| Build all modules | `./gradlew clean bootJar`             |
| Clean all | `./gradlew cleanAll`                  |
| Run integration tests | `./gradlew :integration-tests:test`   |
| Start dev UI | `./gradlew :media-player-ui:npmStart` |
| Start an app | `./gradlew :apps\<app-name\>:bootRun` |
| Build a single app | `./gradlew :apps\<app-name\>:bootJar` |
| Run unit tests for a module | `./gradlew :apps\<app-name\>:test`    |

## App Modules

| App |  Gradle Path |
|---|---|
| admin-endpoint |`:apps:admin-endpoint` |
| media-comment | `:apps:media-comment` |
| media-management | `:apps:media-management` |
| media-metric-endpoint | `:apps:media-metric-endpoint` |
| media-player-endpoint | `:apps:media-player-endpoint` |
| reaction | `:apps:reaction` |
| user-management | `:apps:user-management` |

## Composite Build Structure

The project uses Gradle composite builds. These are included builds (not subprojects):

- `:gradle-plugins` — Precompiled Gradle plugins
- `:util` — Java core utilities
- `:spring-util` — Spring utility modules
- `:drivers` — JPA/database drivers (MySQL, Postgres, Redis, Kafka)
- `:libs` — Domain logic (avro-model, series, users)
- `:qa-endpoint-root` — QA endpoint modules
- `:test-data` — Test data generation
- `:media-player-ui` — Frontend UI

Only `:integration-tests` is a direct subproject of the root.

## Before Running

1. Always `cd` to the repo root before invoking `./gradlew`.
2. First builds will fork a one-off daemon and take longer — subsequent runs are faster.
3. If the user provides a module name as an argument, scope the command to that module.
