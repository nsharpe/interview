pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "test-data"

includeBuild("../gradle-plugins")
includeBuild("../admin-endpoint-root")
includeBuild("../media-management-root")
includeBuild("../user-management-root")
includeBuild("../media-player-endpoint-root")
