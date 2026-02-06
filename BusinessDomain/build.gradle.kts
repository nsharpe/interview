plugins {
    id("java-convention")
}

group = "org.example.driver"

tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}