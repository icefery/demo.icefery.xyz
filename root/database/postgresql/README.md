# PostgreSQL

## 常用命令

- 复制表结构

  ```sql
  CREATE TABLE src ( LIKE dst INCLUDING ALL );
  ```

## 收藏

#### [PostgreSQL 博文、资料、学习笔记、系列教程汇总](https://www.cnblogs.com/aixing/p/14918624.html)

#### [PostgreSQL-with 子句实现递归](https://zhuanlan.zhihu.com/p/159555056)

#### 查看所有表结构信息

```sql
select ('alter table ' || schema_name || '.' || table_name || ' alter column ' || column_name || ' type text;') as sql
from (
    select
        t.table_schema as schema_name,
        t.table_name,
        c.column_name,
        c.ordinal_position as column_order,
        c.data_type as column_type
    from information_schema.tables t
    join information_schema.columns c on c.table_schema = t.table_schema and c.table_name = t.table_name
    where t.table_schema = 'stg' and c.data_type != 'text'
    order by t.table_schema, t.table_name, c.ordinal_position
)
```
