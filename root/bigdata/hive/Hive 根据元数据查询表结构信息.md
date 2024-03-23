## 查询表结构信息

```sql
select
    DBS.name                    as schema_name,
    TBLS.tbl_name               as table_name,
    TABLE_PARAMS.param_value    as table_comment,
    COLUMNS_V2.column_name      as column_name,
    COLUMNS_V2.type_name        as column_type,
    COLUMNS_V2.comment          as column_comment,
    PARTITION_KEYS.pkey_name    as partition_column_name,
    PARTITION_KEYS.pkey_type    as partition_column_type,
    PARTITION_KEYS.pkey_comment as partition_column_comment
from hive.DBS
    left join hive.TBLS           on TBLS.db_id = DBS.db_id
    left join hive.TABLE_PARAMS   on TABLE_PARAMS.tbl_id = TBLS.tbl_id and TABLE_PARAMS.param_key = 'comment'
    left join hive.SDS            on SDS.sd_id = TBLS.sd_id
    left join hive.COLUMNS_V2     on COLUMNS_V2.cd_id = SDS.cd_id
    left join hive.PARTITION_KEYS on PARTITION_KEYS.tbl_id = TBLS.tbl_id
order by DBS.name, TBLS.tbl_name, COLUMNS_V2.integer_idx
```

## 收藏

-   [Hive 笔记 6-hive 元数据表结构详解](https://www.jianshu.com/p/3841d7e89c98)
