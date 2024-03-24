```shell
mvn clean package
```

```shell
cd $HADOOP_HOME

bin/hadoop jar word-count-0.0.1-jar-with-dependencies.jar xyz.icefery.demo.WordCount /input /output

bin/hadoop fs -get /output/part-r-00000 result.txt

cat result.txt
```
