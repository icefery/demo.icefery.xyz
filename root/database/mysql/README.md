# MySQL

## 查看表结构信息

```sql
select
    schemata.schema_name as schema_name,
    tables.table_name,
    tables.table_type,
    tables.table_comment,
    columns.column_name,
    columns.column_type,
    columns.ordinal_position as column_order,
    (case columns.is_nullable when 'YES' then 1 else 0 end) as column_nullable,
    (case columns.column_key  when 'PRI' then 1 else 0 end) as column_primary_key,
    columns.column_default as column_default,
    columns.column_comment as column_comment
from information_schema.schemata
join information_schema.tables on tables.table_schema = schemata.schema_name
join information_schema.columns on columns.table_schema = tables.table_schema and columns.table_name = tables.table_name
where 1 = 1
and schemata.schema_name not in ('sys', 'mysql', 'information_schema', 'performance_schema')
and tables.table_type in ('BASE TABLE', 'VIEW')
order by schema_name, table_name, column_order
```

## 收藏

#### [mysql 设置了 utf8mb4，为什么还有 utf8mb4_general_ci 和 utf8mb4_0900_ai_ci？](https://www.cnblogs.com/seasonhu/p/14994857.html)
