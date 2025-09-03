plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "xyz.icefery.demo"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.apache.hive:hive-exec:2.3.9")
    implementation("cn.hutool:hutool-all:5.8.11")
}

sourceSets {
    main {
        runtimeClasspath += project.configurations.compileClasspath.get()
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations.runtimeClasspath.get())
}
