pluginManagement {
    includeBuild("gradle-plugins")
}

rootProject.name = "MediaPlayer"

includeBuild("gradle-plugins")
includeBuild("AvroModel")
includeBuild("Core")
includeBuild("drivers")
includeBuild("BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}

// Pod setup
includeBuild("kafka-pod")
includeBuild("SpringPod")
includeBuild("SpringRest")

// Rest Deployable
includeBuild("AdminEndpoint")
includeBuild("MediaManagement")
includeBuild("PublicRestEndpoint")
includeBuild("media-player-endpoint-root")
includeBuild("qa-endpoint-root")
includeBuild("media-metric-endpoint-root")

// UI
includeBuild("media-player-ui")

includeBuild("TestData")
include("IntegrationTests")
