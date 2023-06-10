plugins {
    id("java")
    id("org.springframework.boot").version("3.1.0")
    id("io.spring.dependency-management").version("1.1.0")
}

group = "com.example"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("com.mysql:mysql-connector-j")
    implementation("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("cn.hutool:hutool-all:5.8.11")

    implementation("org.jooq:jooq-meta:3.17.7")
    implementation("org.jooq:jooq-codegen:3.17.7")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
