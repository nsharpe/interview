pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "test-data"

includeBuild("../gradle-plugins")
includeBuild("../apps")
includeBuild("../media-management-root")
includeBuild("../media-player-endpoint-root")
