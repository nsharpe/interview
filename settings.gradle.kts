pluginManagement {

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven {
            url = uri("https://packages.confluent.io/maven/")
        }
    }
}

rootProject.name = "MediaPlayer"

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