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

#### 进程管理

```sql
-- 查看数据库当前的进程 看一下有无正在执行的慢 SQL 记录线程
select
    procpid,
    start,
    now() - start as lap,
    current_query
from (
    select
        backendid,
        pg_stat_get_backend_pid(s.backendid) as procpid,
        pg_stat_get_backend_activity_start(s.backendid) as start,
        pg_stat_get_backend_activity(s.backendid) as current_query
    from (
        select pg_stat_get_backend_idset() as backendid
    ) as s
) as s
where current_query != '<idle>'
order by lap desc;

-- 杀事务
select pg_cancel_backend(pid);

--查询具体表的执行情况
select * from pg_stat_activity where query ~ '表名';
```
