plugins {
    id("boot-convention")
}

group = "org.amoeba.example.app"

tasks.bootJar {
    enabled = false
}

tasks.bootRun {
    enabled = false
}


tasks.clean{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "clean" })
    }
}

tasks.build{
    subprojects.forEach { proj ->
        dependsOn(proj.tasks.matching { it.name == "build" })
    }
}