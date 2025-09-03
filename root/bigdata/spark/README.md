# Spark

## 快速开始

### 安装

```shell
wget https://archive.apache.org/dist/spark/spark-3.1.3/spark-3.1.3-bin-without-hadoop.tgz
```

### 配置

-   `conf/spark-env.sh`

    ```shell
    JAVA_HOME=/opt/env/jdk-8
    SPARK_DIST_CLASSPATH=$(/opt/env/hadoop/bin/hadoop classpath)
    SPARK_MASTER_HOST=vm101
    SPARK_MASTER_PORT=7077
    ```

-   `conf/workers`

    ```shell
    vm101
    ```

### 启动

```shell
sbin/start-all.sh
```

## 收藏

#### [Spark 任务提交流程说明](https://blog.csdn.net/ran_hao/article/details/125569872)

#### Spark on JDK-17

-   https://stackoverflow.com/questions/73465937/apache-spark-3-3-0-breaks-on-java-17-with-cannot-access-class-sun-nio-ch-direct
-   https://stackoverflow.com/questions/72724816/running-unit-tests-with-spark-3-3-0-on-java-17-fails-with-illegalaccesserror-cl
-   https://lists.apache.org/thread/814cpb1rpp73zkhtv9t4mkzzrznl82yn
-   https://github.com/apache/spark/blob/v3.3.0/launcher/src/main/java/org/apache/spark/launcher/JavaModuleOptions.java

```shell
-XX:+IgnoreUnrecognizedVMOptions
--add-modules=jdk.incubator.vector
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.invoke=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.io=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/java.nio=ALL-UNNAMED
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED
--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED
--add-opens=java.base/sun.nio.cs=ALL-UNNAMED
--add-opens=java.base/sun.security.action=ALL-UNNAMED
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED
--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED
-Djdk.reflect.useDirectMethodHandle=false
-Dio.netty.tryReflectionSetAccessible=true
```
