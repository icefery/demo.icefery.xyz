### 环境说明

-   `ubuntu-20.04`

    -   启用 `root` 用户 SSH

    -   SSH 免密登录

-   `jdk-8`

    > `hadoop-3.3.1` 版本已经支持 `jdk-11`

-   `zookeeper-3.7`

    > 地址：`win10:2181,win10:2182,win10:2183`

### 参考

-   [hadoop 为什么需要在 hadoop-env.sh 重新配置 JAVA_HOME？](https://www.zhihu.com/question/264588465)
-   [HDFS_NAMENODE_USER, HDFS_DATANODE_USER & HDFS_SECONDARYNAMENODE_USER not defined](https://stackoverflow.com/questions/48129029/hdfs-namenode-user-hdfs-datanode-user-hdfs-secondarynamenode-user-not-defined)
-   [hadoop ha 模式下，kill active 的 namenode 节点后，standby 的 namenode 节点没能自动启动](https://blog.csdn.net/qq_22310551/article/details/85700978)
-   [Install Hadoop 3.3.0 on Windows 10 Step by Step Guide](https://kontext.tech/column/hadoop/447/install-hadoop-330-on-windows-10-step-by-step-guide)
-   [外网无法访问 HDFS 解决方法](https://blog.csdn.net/vaf714/article/details/82996860)

### Preudo-Distributed 模式

#### 配置文件

-   `$HADOOP_HOME/etc/hadoop/hadoop-env.sh`

    ```shell
    export JAVA_HOME=/opt/jdk-8
    export HADOOP_HOME=/opt/hadoop-3.3.1
    export HDFS_NAMENODE_USER=root
    export HDFS_SECONDARYNAMENODE_USER=root
    export HDFS_DATANODE_USER=root
    export YARN_RESOURCEMANAGER_USER=root
    export YARN_NODEMANAGER_USER=root
    ```

-   `$HADOOP_HOME/etc/hadoop/workers`

    ```shell
    node101
    ```

-   `$HADOOP_HOME/etc/hadoop/core-site.xml`

    > `hadoop.tmp.dir` 不能引用环境变量

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://node101:9000</value>
        </property>
        <property>
            <name>hadoop.tmp.dir</name>
            <value>/opt/hadoop-3.3.1/data</value>
        </property>
        <property>
            <name>hadoop.http.staticuser.user</name>
            <value>root</value>
        </property>
        <property>
            <name>hadoop.proxyuser.root.hosts</name>
            <value>*</value>
        </property>
        <property>
            <name>hadoop.proxyuser.root.groups</name>
            <value>*</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/hdfs-site.xml`

    > `dfs.replication` 默认为 3

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>dfs.client.use.datanode.hostname</name>
            <value>true</value>
        </property>
        <property>
            <name>dfs.replication</name>
            <value>1</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/yarn-site.xml`

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
        <property>
            <name>yarn.log-aggregation-enable</name>
            <value>true</value>
        </property>
        <property>
            <name>yarn.log-aggregation.retain-seconds</name>
            <value>604800</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/mapred-site.xml`

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>yarn.app.mapreduce.am.env</name>
            <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
        </property>
        <property>
            <name>mapreduce.map.env</name>
            <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
        </property>
        <property>
            <name>mapreduce.reduce.env</name>
            <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
        </property>
        <property>
            <name>mapreduce.framework.name</name>
            <value>yarn</value>
        </property>
    </configuration>
    ```

#### 格式化 NameNode

```shell
cd $HADOOP_HOME
bin/hdfs namenode -format
```

> 如需重新格式化，需先删除 `$HADOOP_HOME/data/dfs/name` 目录

#### 启动

```shell
cd $HADOOP_HOME
sbin/start-dfs.sh
sbin/start-yarn.sh
bin/mapred --daemon start historyserver
```

> -   HDFS 界面：[http://node101:9870](http://node101:9870)、[http://node101:9868](http://node101:9868)
> -   YARN 界面：[http://node101:8088](http://node101:8088)

### Fully-Distributed 模式

#### 集群规划

| hostname | HDFS                        | YARN                                           |
| -------- | --------------------------- | ---------------------------------------------- |
| node101  | DataNode、NameNode          | NodeManager                                    |
| node102  | DataNode                    | NodeManager、ResourceManager、JobHistoryServer |
| node103  | DataNode、SecondaryNameNode | NodeManager                                    |

#### 配置文件

-   `$HADOOP_HOME/etc/hadoop/hadoop-env.sh`

    > 与 Preudo-Distributed 模式相同

-   `$HADOOP_HOME/etc/hadoop/workers`

    ```shell
    node101
    node102
    node103
    ```

-   `$HADOOP_HOME/etc/hadoop/core-site.xml`

    > 与 Preudo-Distributed 模式相同

-   `$HADOOP_HOME/etc/hadoop/hdfs-site.xml`

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>dfs.client.use.datanode.hostname</name>
            <value>true</value>
        </property>
        <property>
            <name>dfs.namenode.http-address</name>
            <value>node101:9870</value>
        </property>
        <property>
            <name>dfs.namenode.secondary.http-address</name>
            <value>node103:9868</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/yarn-site.xml`

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
        <property>
            <name>yarn.resourcemanager.hostname</name>
            <value>node102</value>
        </property>
        <property>
            <name>yarn.log-aggregation-enable</name>
            <value>true</value>
        </property>
        <property>
            <name>yarn.log.server.url</name>
            <value>http://node102:19888/jobhistory/logs</value>
        </property>
        <property>
            <name>yarn.log-aggregation.retain-seconds</name>
            <value>604800</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/mapred-site.xml`

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
           <property>
            <name>yarn.app.mapreduce.am.env</name>
            <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
        </property>
        <property>
            <name>mapreduce.map.env</name>
            <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
        </property>
        <property>
            <name>mapreduce.reduce.env</name>
            <value>HADOOP_MAPRED_HOME=${HADOOP_HOME}</value>
        </property>
        <property>
            <name>mapreduce.framework.name</name>
            <value>yarn</value>
        </property>
        <property>
            <name>mapreduce.jobhistory.address</name>
            <value>node102:10020</value>
        </property>
        <property>
            <name>mapreduce.jobhistory.webapp.address</name>
            <value>node102:19888</value>
        </property>
    </configuration>
    ```

#### 分发配置

```shell
# node101
rsync -a -r -v $HADOOP_HOME node102:/opt
rsync -a -r -v $HADOOP_HOME node103:/opt
```

#### 格式化 NameNode

```shell
# node101
cd $HADOOP_HOMOE
bin/hdfs namenode -format
```

#### 启动

```shell
cd $HADOOP_HOME
# node101
sbin/start-dfs.sh
# node102
sbin/start-yarn.sh
# node102
bin/mapred --daemon start historyserver
```

#### 验证

-   快捷脚本 `$HADOOP_HOME/run.sh`

    ```shell
    #!/bin/bash
    case $1 in
    start)
    	ssh node101 "${HADOOP_HOME}/sbin/start-dfs.sh"
    	ssh node102 "${HADOOP_HOME}/sbin/start-yarn.sh"
    	ssh node102 "${HADOOP_HOME}/sbin/mapred --daemon start historyserver"
    ;;
    stop)
    	ssh node101 "${HADOOP_HOME}/sbin/stop-dfs.sh"
    	ssh node102 "${HADOOP_HOME}/sbin/stop-yarn.sh"
    	ssh node102 "${HADOOP_HOME}/sbin/mapred stop historyserver"
    ;;
    rsync)
    	for hostname in node101 node102 node103; do
    		if [[ "$(hostname)" != "${hostname}" ]]; then
    			echo "========== ${hostname} =========="
    			rsync -a -r -v "${HADOOP_HOME}" "${hostname}:/opt"
    		fi
    	done
    ;;
    jps)
    	for hostname in node101 node102 node103; do
            echo "========== ${hostname} =========="
            ssh "${hostname}" jps -mlvV
    	done
    ;;
    *)
    	echo "USAGE: $0 <start | stop | rsync | jps>"
    esac
    ```

    ```shell
    chmod +x run.sh
    ./run.sh jps
    ```

### High-Availability 模式

#### 集群规划

| hostname | HDFS               | YARN                                           |
| -------- | ------------------ | ---------------------------------------------- |
| node101  | DataNode、NameNode | NodeManager、ResourceManager                   |
| node102  | DataNode、NameNode | NodeManager、ResourceManager、JobHistoryServer |
| node103  | DataNode、NameNode | NodeManager、ResourceManager                   |

#### 配置文件

-   `$HADOOP_HOME/etc/hadoop/hadoop-env.sh`

    > HA 模式不需要 SecondaryNameNode

    ```shell
    export JAVA_HOME=/opt/jdk-8
    export HADOOP_HOME=/opt/hadoop-3.3.1
    export HDFS_NAMENODE_USER=root
    export HDFS_DATANODE_USER=root
    export HDFS_JOURNALNODE_USER=root
    export HDFS_ZKFC_USER=root
    export YARN_RESOURCEMANAGER_USER=root
    export YARN_NODEMANAGER_USER=root
    ```

-   `$HADOOP_HOME/etc/hadoop/workers`

    > 与 Fully-Distributed 模式相同

-   `$HADOOP_HOME/etc/hadoop/core-site.xml`

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://hdfs-cluster</value>
        </property>
        <property>
            <name>hadoop.tmp.dir</name>
            <value>/opt/hadoop-3.3.1/data</value>
        </property>
        <property>
            <name>hadoop.http.staticuser.user</name>
            <value>root</value>
        </property>
        <property>
            <name>hadoop.proxyuser.root.hosts</name>
            <value>*</value>
        </property>
        <property>
            <name>hadoop.proxyuser.root.groups</name>
            <value>*</value>
        </property>

        <!-- HDFS Zookeeper 地址 -->
        <property>
            <name>ha.zookeeper.quorum</name>
            <value>win10:2181,win10:8182,win10:2183</value>
        </property>

        <!-- YARN Zookeeper 地址 -->
        <property>
            <name>hadoop.zk.address</name>
            <value>win10:2181,win10:2182,win10:2183</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/hdfs-site.xml`

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>dfs.client.use.datanode.hostname</name>
            <value>true</value>
        </property>
        <!-- HDFS NN HA -->
        <property>
            <name>dfs.nameservices</name>
            <value>hdfs-cluster</value>
        </property>
        <property>
            <name>dfs.ha.namenodes.hdfs-cluster</name>
            <value>nn1, nn2, nn3</value>
        </property>

        <!-- HDFS NN rpc -->
        <property>
            <name>dfs.namenode.rpc-address.hdfs-cluster.nn1</name>
            <value>node101:8020</value>
        </property>
        <property>
            <name>dfs.namenode.rpc-address.hdfs-cluster.nn2</name>
            <value>node102:8020</value>
        </property>
        <property>
            <name>dfs.namenode.rpc-address.hdfs-cluster.nn3</name>
            <value>node103:8020</value>
        </property>

        <!-- HDFS NN http -->
        <property>
            <name>dfs.namenode.http-address.hdfs-cluster.nn1</name>
            <value>node101:9870</value>
        </property>
        <property>
            <name>dfs.namenode.http-address.hdfs-cluster.nn2</name>
            <value>node102:9870</value>
        </property>
        <property>
            <name>dfs.namenode.http-address.hdfs-cluster.nn3</name>
            <value>node103:9870</value>
        </property>

        <!-- HDFS JournalNode -->
        <property>
            <name>dfs.namenode.shared.edits.dir</name>
            <value>qjournal://node101:8485;node102:8485;node103:8485/hdfs-cluster</value>
        </property>
        <property>
            <name>dfs.journalnode.edits.dir</name>
            <value>/opt/hadoop-3.3.1/data/dfs/journalnode/</value>
        </property>

        <!-- HDFS fencing -->
        <property>
            <name>dfs.ha.fencing.methods</name>
            <value>
                sshfence
                shell(/bin/true)
            </value>
        </property>
        <property>
            <name>dfs.ha.fencing.ssh.private-key-files</name>
            <value>/root/.ssh/id_rsa</value>
        </property>

        <!-- HDFS automatic failover -->
        <property>
            <name>dfs.ha.automatic-failover.enabled</name>
            <value>true</value>
        </property>
        <property>
            <name>dfs.client.failover.proxy.provider.hdfs-cluster</name>
            <value>org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/yarn-site.xml`

    ```xml
    <?xml version="1.0"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>

        <!-- YARN RM HA -->
        <property>
            <name>yarn.resourcemanager.ha.enabled</name>
            <value>true</value>
        </property>
        <property>
            <name>yarn.resourcemanager.cluster-id</name>
            <value>yarn-cluster</value>
        </property>
        <property>
            <name>yarn.resourcemanager.ha.rm-ids</name>
            <value>rm1, rm2, rm3</value>
        </property>

        <!-- YARN RM -->
        <property>
            <name>yarn.resourcemanager.hostname.rm1</name>
            <value>node101</value>
        </property>
        <property>
            <name>yarn.resourcemanager.hostname.rm2</name>
            <value>node102</value>
        </property>
        <property>
            <name>yarn.resourcemanager.hostname.rm3</name>
            <value>node103</value>
        </property>

        <!-- YARN RM http -->
        <property>
            <name>yarn.resourcemanager.webapp.address.rm1</name>
            <value>node101:8088</value>
        </property>
        <property>
            <name>yarn.resourcemanager.webapp.address.rm2</name>
            <value>node102:8088</value>
        </property>
        <property>
            <name>yarn.resourcemanager.webapp.address.rm3</name>
            <value>node103:8088</value>
        </property>

        <!-- YARN RM recovery -->
        <property>
            <name>yarn.resourcemanager.recovery.enabled</name>
            <value>true</value>
        </property>
        <property>
            <name>yarn.resourcemanager.store.class</name>
            <value>org.apache.hadoop.yarn.server.resourcemanager.recovery.ZKRMStateStore</value>
        </property>
    </configuration>
    ```

-   `$HADOOP_HOME/etc/hadoop/mapred-site.xml`

> 与 Full-Distributed 模式相同

#### 分发配置

> 与 Full-Distributed 模式相同

#### 启动 JournalNode

```shell
cd $HADOOP_HOME
# node101 | node102 | node103
bin/hdfs --daemon start journalnode
```

#### 格式化 NameNode

```shell
# node101
bin/hdfs namenode -format
# node102 | node103
bin/hdfs namenode -bootstrapStandby
```

#### 格式化 ZK

```shell
# node102
sbin/hdfs zkfs -formatZK
```

#### 启动

```shell
sbin/start-dfs.sh
sbin/start-yarn.sh

# node102
bin/mapred --daemon start historyserver
```

#### HA 状态

```shell
# 查看
bin/hdfs haadmin -getAllServiceState
bin/yarn rmadmin -getAllServiceState

# 手动切换
bin/hdfs haadmin -transitionToActive --forceactive
bin/yarn rmadmin -transitionToActive --forceactive
```

## WordCount

#### 示例

-   文本

    ```shell
    cd $HADOOP_HOME

    mkdir -p input

    cat > input/word.txt <<- EOF
    i keep saying no
    this can not be the way it was supposed to be
    i keep saying no
    there has gotta be a way to get you close to me
    EOF
    ```

-   上传

    ```shell
    bin/hadoop fs -mkdir /input

    bin/hadoop fs -put input/word.txt /input
    ```

-   执行

    ```shell
    hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.3.1.jar wordcount /input /output
    ```

### HDFS 客户端

-   `winutils`

    ```shell
    wget https://github.com/kontext-tech/winutils/blob/master/hadoop-3.3.1/bin/winutils.exe

    mkdir -p /d/env/hadoop-3.3.1/bin

    mv winutils.exe /d/env/hadoop-3.3.1/bin
    ```

    > 配置 `HADOOP_HOME` 到环境变量

-   `pom.xml`

    ```xml
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-client</artifactId>
        <version>3.3.1</version>
    </dependency>
    ```

-   在客户端模拟 Hadoop 集群 DataNode 的 `hostname`

    ```shell
    echo '192.168.137.101 node101' >> /c/Windows/System32/drivers/etc/hosts
    ```

-   下载文件

    ```java
    public class Main {
        public static final String CLASSPATH = Main.class.getResource("/").toString();

        public static void main(String[] args) throws IOException, InterruptedException {
            Configuration conf = new Configuration();
            conf.set("dfs.client.use.datanode.hostname", "true");
            FileSystem fs = FileSystem.get(URI.create("hdfs://node101:9000"), conf, "root");
            fs.copyToLocalFile(new Path("/input/word.txt"), new Path(CLASSPATH + "/word.txt"));
            fs.close();
        }
    }
    ```

### MapReduce 程序

-   `pom.xml`

    ```xml
    <dependency>
        <groupId>org.apache.hadoop</groupId>
        <artifactId>hadoop-client</artifactId>
        <version>3.3.1</version>
    </dependency>
    ```

    ```xml
    <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <configuration>
            <descriptorRefs>
                <descriptorRef>jar-with-dependencies</descriptorRef>
            </descriptorRefs>
        </configuration>
        <executions>
            <execution>
                <id>make-assembly</id>
                <phase>package</phase>
                <goals>
                    <goal>single</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    ```

-   WordCount

    ```java
    public class WordCount {
        public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf);

            job.setJarByClass(WordCount.class);

            job.setMapperClass(WordCount.WordCountMapper.class);
            job.setReducerClass(WordCount.WordCountReducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            FileInputFormat.setInputPaths(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            boolean result = job.waitForCompletion(true);
            System.exit(result ? 0 : 1);
        }

        public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
            @Override
            protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
                String line = value.toString();
                String[] words = line.split(" ");
                for (String word : words) {
                    context.write(new Text(word), new IntWritable(1));
                }
            }
        }

        public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
            @Override
            protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
                int count = 0;
                for (IntWritable value : values) {
                    count += value.get();
                }
                context.write(key, new IntWritable(count));
            }
        }
    }
    ```

-   打包

    ```xml
    mvn clean package
    ```

-   执行

    ```shell
    cd $HADOOP_HOME

    bin/hadoop jar word-count-0.0.1-jar-with-dependencies.jar xyz.icefery.demo.WordCount /input /output

    bin/hadoop fs -get /output/part-r-00000 result.txt

    cat result.txt
    ```
