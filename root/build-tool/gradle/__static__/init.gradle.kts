initscript {
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/nexus/content/groups/public")
        maven("https://maven.aliyun.com/nexus/content/repositories/spring")
        maven("https://maven.aliyun.com/nexus/content/repositories/spring-plugin")
        maven("https://maven.aliyun.com/nexus/content/repositories/google")
        maven("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        mavenCentral()
    }
}

allprojects {
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/nexus/content/groups/public")
        maven("https://maven.aliyun.com/nexus/content/repositories/spring")
        maven("https://maven.aliyun.com/nexus/content/repositories/spring-plugin")
        maven("https://maven.aliyun.com/nexus/content/repositories/google")
        maven("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        mavenCentral()
    }
    buildscript {
        repositories {
            mavenLocal()
            maven("https://maven.aliyun.com/nexus/content/groups/public")
            maven("https://maven.aliyun.com/nexus/content/repositories/spring")
            maven("https://maven.aliyun.com/nexus/content/repositories/spring-plugin")
            maven("https://maven.aliyun.com/nexus/content/repositories/google")
            maven("https://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
            mavenCentral()
        }
    }
}
