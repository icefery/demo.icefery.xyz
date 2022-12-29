plugins {
    java
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "xyz.icefery.demo"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // spring-boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // mybatis-plus
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.2")
    // redisson
    implementation("org.redisson:redisson-spring-boot-starter:3.18.0")
    // jdbc
    runtimeOnly("com.mysql:mysql-connector-j")
    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
