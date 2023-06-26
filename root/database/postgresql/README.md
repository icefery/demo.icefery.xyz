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

#### 修改序列起始值

> 针对不支持 `ALTER SEQUENCE` 操作的场景。

```sql
-- 创建临时序列
CREATE SEQUENCE pms.temp_id_seq INCREMENT BY 1 START 1;
-- 绑定临时序列
ALTER TABLE pms.pms_product ALTER COLUMN id SET DEFAULT nextval('pms.temp_id_seq');
-- 重建原有序列
DROP SEQUENCE pms.pms_product_id_seq;
CREATE SEQUENCE pms.pms_product_id_seq INCREMENT BY 1 START 1000000;
-- 绑定原有序列
ALTER TABLE pms.pms_product ALTER COLUMN id SET DEFAULT nextval('pms.pms_product_id_seq');
```

#### 数组截取

> 提取表名中的信息。

```sql
select segments[2] as province_code, segments[3] as topic_code, array_to_string(segments[4:(array_length(segments, 1))], '_')
from (
    select string_to_array(table_name, '_') as segments
    from (
        select 'stg_bi_topic1_t_a_b_l_e_1' as table_name union all
        select 'stg_tj_topic2_t_a_b_l_e_2' as table_name union all
        select 'stg_sh_topic3_t_a_b_l_e_3' as table_name union all
        select 'stg_cq_topic4_t_a_b_l_e_4' as table_name
    ) t1
) t2
```

| province_code | topic_code | table_name  |
| ------------- | ---------- | ----------- |
| bi            | topic1     | t_a_b_l_e_1 |
| tj            | topic2     | t_a_b_l_e_2 |
| sh            | topic3     | t_a_b_l_e_3 |
| cq            | topic4     | t_a_b_l_e_4 |

#### 查看表结构信息

> [PostgreSQL 中系统表](https://blog.csdn.net/qq_33459369/article/details/124021543)

```sql
create or replace view metadata AS
select
    pg_namespace.nspname          as schema_name,
    pg_class.relname              as table_name,
    obj_description(pg_class.oid) as table_comment,
    pg_attribute.attname          as column_name,
    concat_ws('', pg_type.typname, substring(format_type(pg_attribute.atttypid, pg_attribute.atttypmod) from '\(.*\)')) as column_type,
    pg_attribute.attnum           as column_order,
    pg_description.description    as column_comment
from      pg_catalog.pg_namespace
left join pg_catalog.pg_class       on pg_class.relnamespace = pg_namespace.oid
left join pg_catalog.pg_attribute   on pg_attribute.attrelid = pg_class.oid
left join pg_catalog.pg_type        on pg_type.oid           = pg_attribute.atttypid
left join pg_catalog.pg_description on pg_description.objoid = pg_attribute.attrelid and pg_description.objsubid = pg_attribute.attnum
where pg_class.relkind = 'r' and pg_attribute.attnum > 0
order by schema_name, table_name, column_order
```

#### PG 拿到所有 ID 放在 IN 中

> `''` 表示转义一个单引号

```sql
select array_to_string(array_agg('''' || id ||''''), ', ') from a;
```
