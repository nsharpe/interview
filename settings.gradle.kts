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
// Rest Deployable
includeBuild("qa-endpoint-root")

// UI
includeBuild("media-player-ui")
includeBuild("test-data")

include("integration-tests")
