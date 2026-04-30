# Gradle Plugins

Custom Gradle convention plugins that standardize the build across all modules in the media ecosystem.

## Plugins

| Plugin | Description | Example Usage |
|---------|-------------|---------------|
| [java-convention](src/main/kotlin/java-convention.gradle.kts) | Base Java setup — Java 21 toolchain, repositories, JUnit 5, SpotBugs, Lombok | [Root build.gradle.kts](../build.gradle.kts) |
| [boot-convention](src/main/kotlin/boot-convention.gradle.kts) | Spring Boot application defaults — bootJar, docker-compose integration, database drivers | N/A (extended by other plugins) |
| [boot-library](src/main/kotlin/boot-library.gradle.kts) | Spring Boot library module defaults — disables bootJar/bootRun, extends boot-convention | [kafka-driver/build.gradle.kts](../drivers/kafka-driver/build.gradle.kts) |
| [web-documentation](src/main/kotlin/web-documentation.gradle.kts) | OpenAPI doc generation — builds on boot-convention and springdoc | N/A (extended by web-convention) |
| [web-convention](src/main/kotlin/web-convention.gradle.kts) | Full web app setup — boot-convention + web-documentation + dynamic port for API spec scraping | [media-player-endpoint/webapp/build.gradle.kts](../apps/media-player-endpoint/webapp/build.gradle.kts) |
| [web-sdk](src/main/kotlin/web-sdk.gradle.kts) | Java SDK generation from OpenAPI specs (webclient + Lombok) | [qa-endpoint-sdk/build.gradle.kts](../qa-endpoint-root/qa-endpoint-sdk/build.gradle.kts) |
| [typescript-sdk](src/main/kotlin/typescript-sdk.gradle.kts) | TypeScript SDK generation from OpenAPI specs (axios) | [qa-endpoint-typescript-sdk/build.gradle.kts](../qa-endpoint-root/qa-endpoint-typescript-sdk/build.gradle.kts) |

## Adding a New Plugin

1. Create a new file `<name>.gradle.kts` inside `gradle-plugins/src/main/kotlin/`.
2. If the plugin depends on other convention plugins, declare them in the `plugins` block using `id("<name>")`.
3. No changes to `settings.gradle.kts` are needed — the `gradle-plugins` module is already included as a composite build:

```kotlin
// settings.gradle.kts (root)
pluginManagement {
    includeBuild("gradle-plugins")
}
```

## How to Apply Plugins

In any module's `build.gradle.kts`, add the plugin under the `plugins` block using the file name (without the `.gradle.kts` extension):

```kotlin
plugins {
    id("web-convention")
}
```

### SDK Plugins Configuration

The `web-sdk` and `typescript-sdk` plugins require a `sdkConfig` block pointing to an OpenAPI spec file:

```kotlin
// Java SDK
plugins {
    id("web-sdk")
}

sdkConfig {
    specFile.set(file("api-spec.json"))
    basePackage.set("org.amoeba.example.sdk")
}
```

```kotlin
// TypeScript SDK
plugins {
    id("typescript-sdk")
}

sdkConfig {
    specFile.set(file("api-spec.json"))
    npmName.set("@amoeba/example-sdk")
}
```

## Dependencies

Built against Spring Boot 3.5.10 with SpotBugs, Lombok, OpenAPI Generator, and Springdoc plugins.
