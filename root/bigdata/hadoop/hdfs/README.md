# HDFS

### 组成架构

![](https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/images/hdfsarchitecture.png)

### 常用命令

```shell
# 上传
hadoop fs -put <LOCAL> <HDFS>

# 追加
hadoop -appendToFile <LOCAL> <HDFS>

# 下载
hadoop fs -get <HDFS> <LOCAL>

# 设置副本数量
hadoop fs -setrep <REPLICATION> <HDFS>
```
