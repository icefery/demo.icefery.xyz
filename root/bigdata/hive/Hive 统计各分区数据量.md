## 统计各分区数据量

```sql
select t1.schema_name, t1.table_name, t2.table_comment, t3.partition_name, t4.num_files, t4.num_rows, t4.total_size
from (
    select TBLS.tbl_id, DBS.name as schema_name, TBLS.tbl_name as table_name
    from hive.DBS
    join hive.TBLS ON TBLS.db_id = DBS.db_id
) t1
left join (
    select tbl_id, param_value as table_comment
    from hive.table_params
    where param_key = 'comment'
) t2
left join (
    select part_id, tbl_id, part_name as partition_name
    from hive.PARTITIONS
) t3
left join (
    select
      part_id,
        max(if(param_key = 'numFiles',  param_value + 0, null)) as num_files,
        max(if(param_key = 'numRows',   param_value + 0, null)) as num_rows,
        max(if(param_key = 'totalSize', param_value + 0, null)) as total_size
    from hive.PARTITION_PARAMS
    group by part_id
) t4 on t4.part_id = t3.part_id
order by t1.schema_name, t1.table_name, t3.partition_name desc
```
