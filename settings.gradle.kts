pluginManagement {
    includeBuild("Plugins")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://packages.confluent.io/maven/")
        }
    }
}

rootProject.name = "MediaPlayer"

includeBuild("Plugins")
includeBuild("AvroModel")

include("Core")
include("PublicRestEndpoint")
include("MySqlDriver")
include("TestData")
include("IntegrationTests")
include("MediaManagement")
include("Users")
include("SpringPod")
include("SpringWeb")
include("Series")
include("MediaManagementSdk")
include("PublicRestEndpointSdk")
include("MediaPlayerEndpoint")
include("Kafka")
include("Security")
include("Redis")
include("MediaPlayerEndpointSdk")
include("KafkaPod")
include("PostgressqlDriver")
