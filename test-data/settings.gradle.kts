pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "test-data"

includeBuild("../gradle-plugins")
includeBuild("../admin-endpoint-root")
includeBuild("../MediaManagement")
includeBuild("../PublicRestEndpoint")
includeBuild("../media-player-endpoint-root")
