plugins {
    id("java")
    id("application")
}

group = "ru.devildeveloper74"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

var mainClassName = "ru.devildeveloper74.Main"

tasks.jar {
    manifest {
        attributes["Main-Class"] = mainClassName
    }
}

tasks.register<Jar>("fatJar") {
    manifest {
        attributes["Main-Class"] = mainClassName
    }
    archiveBaseName.set(rootProject.name)
    archiveVersion.set("")
    archiveClassifier.set("")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
}