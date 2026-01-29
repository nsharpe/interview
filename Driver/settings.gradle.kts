pluginManagement {
    includeBuild("../Plugins")
}

rootProject.name="driver"

includeBuild("../Plugins")
includeBuild("../AvroModel")
include("mysql-driver")
include("postgres-driver")
include("kafka-driver")
include("redis-driver")
