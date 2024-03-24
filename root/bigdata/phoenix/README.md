# Phoenix

## 快速开始

#### HBase 配置文件

-   `hbase-site.xml`

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration>
        <property>
            <name>hbase.cluster.distributed</name>
            <value>true</value>
        </property>
        <property>
            <name>hbase.rootdir</name>
            <value>hdfs://vm101:9000/hbase</value>
        </property>
        <property>
            <name>hbase.zookeeper.quorum</name>
            <value>vm101:2181</value>
        </property>
        <property>
            <name>hbase.regionserver.wal.codec</name>
            <value>org.apache.hadoop.hbase.regionserver.wal.IndexedWALEditCodec</value>
        </property>
        <property>
            <name>phoenix.schema.isNamespaceMappingEnabled</name>
            <value>true</value>
        </property>
    </configuration>
    ```

```shell
cp $PHOENIX_HOME/phoenix-server-hbase-2.3-5.1.2.jar $HBASE_HOME/lib

ln -sf $HBASE_HOME/conf/hbase-site.xml $PHOENIX_HOME/bin/hbase-site.xml
```
