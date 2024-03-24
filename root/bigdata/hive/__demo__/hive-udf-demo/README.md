## 注册函数

```shell
bin/hdfs dfs -mkdir -p /hive
bin/hdfs dfs -put hive-udf-demo-0.0.1-all.jar /hive
```

```sql
drop function if exists zodiac;
create function zodiac as 'xyz.icefery.demo.Zodiac' using jar 'hdfs:///hive/hive-udf-demo-0.0.1-all.jar';

drop function if exists json_array_flat;
create function json_array_flat as 'xyz.icefery.demo.JsonArrayFlat' using jar 'hdfs:///hive/hive-udf-demo-0.0.1-all.jar';
```
