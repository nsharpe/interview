pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name = "test-data"

includeBuild("../gradle-plugins")
includeBuild("../AdminEndpoint")
includeBuild("../MediaManagement")
includeBuild("../PublicRestEndpoint")
includeBuild("../media-player-endpoint-root")
