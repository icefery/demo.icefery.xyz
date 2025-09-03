### 环境说明

-   `ubuntu-20.04`

-   `jdk-8`

    > `hbase-2.3.6` 支持 `jdk-11`

-   `hadoop-3.3.1`
-   `zookeeper-3.7`

### Preudo-Distributed 模式

#### 配置文件

-   `$HBASE_HOME/conf/hbase-env.sh`

    ```shell
    export JAVA_HOME=/opt/jdk-8
    export HBASE_MANAGES_ZK=false
    export HBASE_DISABLE_HADOOP_CLASSPATH_LOOKUP=true
    ```

-   `$HBASE_HOME/conf/hbase-site.xml`

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration>
        <property>
            <name>hbase.cluster.distributed</name>
            <value>true</value>
        </property>
        <property>
            <name>hbase.rootdir</name>
            <value>hdfs://node101:9000/hbase</value>
        </property>
        <property>
            <name>hbase.zookeeper.quorum</name>
            <value>win10:2181,win10:2182,win10:2183</value>
        </property>
    </configuration>
    ```

    > 整合 HDFS HA 模式：
    >
    > -   拷贝 HDFS 配置文件
    >
    >     ```shell
    >     ln -s $HADOOP_HOME/etc/hadoop/core-site.xml $HBASE_HOME/conf/core-site.xml
    >     ln -s $HADOOP_HOME/etc/hadoop/hdfs-ste.xml $HBASE_HOME/conf/hdfs-site.xml
    >     ```
    >
    > -   修改 `hbase-site.xml`
    >
    >     ```properties
    >     hbase.rootdir = hdfs://hdfs-cluster/hbase
    >     ```

-   `$HBASE_HOME/conf/regionservers`

    ```shell
    node101
    ```

#### 启动

```shell
cd $HBASE_HOME
bin/start-hbase.sh
```

> WEB 界面 [http://node101:16010](http://node101:16010)、[http://node101:16030](http://node101:16010)

#### 连接

```shell
cd $HBASE_HOME
bin/hbase shell
```

### Fully-Distributed 模式

#### 配置文件

-   `$HBASE_HOME/conf/regionservers`

    ```shell
    node101
    node102
    node103
    ```

### HA 模式

#### 配置文件

-   `$HBASE_HOME/conf/backup-masters`

    ```shell
    node102
    node103
    ```
