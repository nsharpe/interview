pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name="Driver"

include("mysql-driver")
include("postgres-driver")
include("kafka-driver")
include("redis-driver")

includeBuild("../Plugins")
includeBuild("../AvroModel")

