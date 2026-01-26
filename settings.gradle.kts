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
include("AvroModel")
include("MediaPlayerEndpoint")
include("Kafka")
include("Security")
include("Redis")
include("MediaPlayerEndpointSdk")
include("KafkaPod")
include("PostgressqlDriver")
