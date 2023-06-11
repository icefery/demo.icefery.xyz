plugins {
    id("scala")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "org.example"
version = "0.0.1"
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    // scala
    compileOnly("org.scala-lang:scala-library:2.12.15")
    compileOnly("org.scala-lang:scala-reflect:2.12.15")
    compileOnly("org.scala-lang:scala-compiler:2.12.15")
    // spark
    compileOnly("org.apache.spark:spark-core_2.12:3.3.2")
    compileOnly("org.apache.spark:spark-sql_2.12:3.3.2")
    compileOnly("org.apache.spark:spark-streaming_2.12:3.3.2")
    // spark-kafka
    implementation("org.apache.spark:spark-streaming-kafka-0-10_2.12:3.3.2")
    // jdbc
    implementation("com.alibaba:druid:1.2.16")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.postgresql:postgresql:42.6.0")
}

configurations {
    compileOnly {
        isCanBeResolved = true
    }
}

sourceSets {
    main {
        runtimeClasspath += project.configurations.compileOnly.get()
    }
    test {
        runtimeClasspath += project.configurations.compileOnly.get()
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations.runtimeClasspath.get())
}
