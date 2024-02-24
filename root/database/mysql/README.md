# MySQL

## 元数据

### 表结构信息

```sql
select
    tables.table_schema                                                                                as schema_name,
    tables.table_name                                                                                  as table_name,
    (case when tables.table_type = 'BASE TABLE' then 't' when tables.table_type = 'VIEW' then 'v' end) as table_type,
    tables.table_comment                                                                               as table_comment,
    columns.column_name                                                                                as column_name,
    columns.ordinal_position                                                                           as column_order,
    columns.column_type                                                                                as column_type,
    (case when columns.is_nullable = 'YES' then 1 else 0 end)                                          as column_nullable,
    (case when columns.column_key  = 'PRI' then 1 else 0 end)                                          as column_primary_key,
    columns.column_default                                                                             as column_default,
    columns.column_comment                                                                             as column_comment
from information_schema.tables
join information_schema.columns on columns.table_schema = columns.table_schema and columns.table_name = tables.table_name
where 1 = 1
and tables.table_schema not in ('information_schema','mysql', 'performance_schema', 'sys')
order by tables.table_schema, tables.table_name, columns.ordinal_position
```

## 收藏

#### [mysql 设置了 utf8mb4，为什么还有 utf8mb4_general_ci 和 utf8mb4_0900_ai_ci？](https://www.cnblogs.com/seasonhu/p/14994857.html)
