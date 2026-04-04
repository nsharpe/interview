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
includeBuild("media-management-root")
includeBuild("media-player-endpoint-root")
includeBuild("qa-endpoint-root")

includeBuild("media-metric-endpoint-root")
// UI
includeBuild("media-player-ui")
includeBuild("test-data")

include("integration-tests")
