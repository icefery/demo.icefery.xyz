### 环境说明

-   `ubuntu-20.04`

-   `jdk-8`

    > `hive-2.3.9`支持 `jdk-11`，但 `hive-3.1.2` 不支持

-   `hadoop-3.3.1`

-   `mysql-8.0`

    ```sql
    CREATE DATABASE hive;
    ```

    ```sql
    CREATE USER 'hive'@'%' IDENTIFIED BY 'hive';
    GRANT ALL PRIVILEGES ON hive.* TO 'hive'@'%';
    FLUSH PRIVILEGES;
    ```

### 参考

-   [hive 使用 hiveserver2、beeline 启动踩过的坑 User: root is not allowed to impersonate root](https://blog.csdn.net/weixin_42404102/article/details/107711323)

### 远程模式

#### 解决 JAR 包冲突

```shell
rm $HIVE_HOME/lib/guava-19.0.jar

cp $HADOOP_HOME/share/hadoop/common/lib/guava-27.0-jre.jar $HIVE_HOME/lib
```

#### 配置文件

-   `$HIVE_HOME/conf/hive-site.xml`

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
            <value>jdbc:mysql://win10:3306/hive</value>
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
            <value>thrift://node101:9083</value>
        </property>
        <property>
            <name>hive.server2.thrift.bind.host</name>
            <value>node101</value>
        </property>
    </configuration>
    ```

#### 初始化元数据库

-   下载驱动

    ```shell
    wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.18/mysql-connector-java-8.0.18.jar -P $HIVE_HOME/lib
    ```

-   初始化

    ```shell
    cd $HIVE_HOME
    bin/schematool -initSchema -dbType mysql
    ```

#### 启动

-   启动 MetaStore

    ```shell
    nohup bin/hive --service metastore 1>/dev/null 2>&1 &
    ```

-   启动 HiveServer2

    ```shell
    nohup bin/hive --service hiveserver2 1>/dev/null 2>&1 &
    ```

-   便捷脚本 `run.sh`

    ```shell
    #!/bin/bash
    case $1 in
    start)
      nohup bin/hive --service metastore 1>/dev/null 2>&1 &
      nohup bin/hive --service hiveserver2 1>/dev/null 2>&1 &
    ;;
    stop)
      kill -9 $(jps -ml | grep 'HiveMetaStore' | awk '{print $1}')
      kill -9 $(jps -ml | grep 'HiveServer2' | awk '{print $1}')
    ;;
    *)
      echo "USAGE: $0 <start | stop>"
    esac
    ```

    ```shell
    chmod +x run.sh
    ./run.sh start
    ```

-   WEB 界面 [http://node101:10002](http://node101:10002)

#### 连接

-   CLI 方式

    ```shell
    bin/hive
    ```

-   JDBC 方式

    ```shell
    bin/beeline -u jdbc:hive2://node101:10000 -n roo
    ```
