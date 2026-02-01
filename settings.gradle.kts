pluginManagement {
    includeBuild("Plugins")
}

rootProject.name = "MediaPlayer"

includeBuild("Plugins")
includeBuild("AvroModel")
includeBuild("Core")
includeBuild("Driver")
includeBuild("SpringPod")
includeBuild("SpringRest")
includeBuild("BusinessDomain")
{
    dependencySubstitution {
        substitute(module("org.example.business-domain:series"))
            .using(project(":Series"))
        substitute(module("org.example.business-domain:users"))
            .using(project(":Users"))
    }
}
includeBuild("AdminEndpoint")
includeBuild("MediaManagement")
includeBuild("PublicRestEndpoint")
includeBuild("media-player-endpoint-root")
includeBuild("qa-endpoint-root")

includeBuild("TestData")
include("IntegrationTests")
include("KafkaPod")
