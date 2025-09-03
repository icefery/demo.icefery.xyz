plugins {
    java
    id("org.springframework.boot") version "2.6.11"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "xyz.icefery.demo"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // spring-boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    // spring-cloud
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.0")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    // spring-cloud-alibaba
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-seata")
    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:2021.0.4.0")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.4")
    }
}
