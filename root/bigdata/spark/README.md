# Spark

## 快速开始

### 安装

```bash
wget https://archive.apache.org/dist/spark/spark-3.1.3/spark-3.1.3-bin-without-hadoop.tgz
```

### 配置

-   `conf/spark-env.sh`

    ```bash
    JAVA_HOME=/opt/env/jdk-8
    SPARK_DIST_CLASSPATH=$(/opt/env/hadoop/bin/hadoop classpath)
    SPARK_MASTER_HOST=vm101
    SPARK_MASTER_PORT=7077
    ```

-   `conf/workers`

    ```bash
    vm101
    ```

### 启动

```bash
sbin/start-all.sh
```

## 收藏

#### [Spark 任务提交流程说明](https://blog.csdn.net/ran_hao/article/details/125569872)
