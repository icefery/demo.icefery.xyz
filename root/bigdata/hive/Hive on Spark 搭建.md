## 版本

|        |                  |
| :----- | :--------------- |
| jdk    | `8`              |
| hadoop | `3.1.3`          |
| spark  | `3.1.3`          |
| hive   | `3.1.3` 手动编译 |

## Hive 源码编译

### 修改

> [hive3.1.4 源码编译兼容 spark3.0.0 hive on spark hadoop3.x 修改源码依赖 步骤详细](https://blog.csdn.net/weixin_52918377/article/details/117123969)

-   升级 Guava 依赖到 `27.0-jre` 版本

-   解决 [HIVE-19316](https://issues.apache.org/jira/browse/HIVE-19316) BUG

-   升级 Spark 依赖到 `3.1.3` 版本

### 编译

```shell
mvn clean package -Pdist -DskipTests -Dmaven.javadoc.skip=true -fae
```

## Hive on Spark

### Hive 配置

-   上传 Spark 的 JAR 包到 HDFS

    ```shell
    $HADOOP_HOME/bin/hdfs dfs -mkdir /spark-jars

    $HADOOP_HOME/bin/hdfs dfs -put $SPARK_HOME/jars/* /spark-jars
    ```

-   `conf/spark-defaults.conf`

    ```shell
    cp $SPARK_HOME/conf/spark-defaults.conf.template conf/spark-defaults.conf
    ```

    ```properties
    spark.master                     yarn
    spark.eventLog.enabled           true
    spark.eventLog.dir               hdfs://vm101:9000/spark-history
    spark.executor.memory            1g
    spark.driver.memory              1g
    ```

-   `conf/hive-site.xml`

    ```xml
    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>javax.jdo.option.ConnectionDriverName</name>
            <value>com.mysql.cj.jdbc.Driver</value>
        </property>
        <property>
            <name>javax.jdo.option.ConnectionURL</name>
            <value>jdbc:mysql://vm101:3306/hive</value>
        </property>
        <property>
            <name>javax.jdo.option.ConnectionUserName</name>
            <value>root</value>
        </property>
        <property>
            <name>javax.jdo.option.ConnectionPassword</name>
            <value>root</value>
        </property>
        <property>
            <name>hive.metastore.uris</name>
            <value>thrift://vm101:9083</value>
        </property>
        <property>
            <name>hive.server2.thrift.bind.host</name>
            <value>vm101</value>
        </property>
        <property>
            <name>hive.execution.engine</name>
            <value>spark</value>
        </property>
        <property>
            <name>spark.yarn.jars</name>
            <value>hdfs://vm101:9000/spark-jars/*</value>
        </property>
    </configuration>
    ```
