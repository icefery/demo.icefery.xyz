# Spring Boot

## 收藏

#### [spring webflux 系列之上传单文件与多文件到七牛云篇](https://blog.csdn.net/BanQIJane/article/details/117296535)

#### [如何在 Spring Boot 应用中优雅的使用 Date 和 LocalDateTime](https://blog.csdn.net/weixin_47083537/article/details/107067508)

#### [SPRINGBOOT 启动流程及其原理](https://blog.csdn.net/gbz2000/article/details/114528096)

#### [使用 Buildpacks 构建原生镜像](https://springdoc.cn/spring-boot/native-image.html#native-image.developing-your-first-application.buildpacks)

#### [Java 容器化指北](https://mritd.com/2022/11/08/java-containerization-guide/)

## Spring Boot GraalVM

> [ubuntu 中编译报错 /usr/bin/ld: cannot find -lperl](https://www.cnblogs.com/0820LL/p/16454927.html)

### 快速开始

```shell
# /usr/bin/ld: cannot find -lz: No such file or directory
apt install libz-dev -y
# 构建可执行文件
mvn clean native:compile -Pnative -Dmaven.test.skip=true
# 构建镜像
mvn spring-boot:build-image -Pnative -Dmaven.test.skip=true
```
