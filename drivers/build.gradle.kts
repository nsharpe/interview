plugins {
    id("java-convention")
}

group = "org.example.drivers"

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}