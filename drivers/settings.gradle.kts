pluginManagement {
    includeBuild("../gradle-plugins")
}

rootProject.name="drivers"

include("mysql-driver")
include("postgres-driver")
include("kafka-driver")
include("redis-driver")

includeBuild("../gradle-plugins")
includeBuild("../AvroModel")

