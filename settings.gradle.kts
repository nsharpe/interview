pluginManagement {
    includeBuild("gradle-plugins")
}

rootProject.name = "media-ecosystem"

includeBuild("gradle-plugins")
includeBuild("util")
includeBuild("spring-util")
includeBuild("drivers")
includeBuild("libs")
includeBuild("apps")

// Qa Specific modules
includeBuild("qa-endpoint-root")
includeBuild("test-data")

// UI
includeBuild("media-player-ui")

include("integration-tests")
