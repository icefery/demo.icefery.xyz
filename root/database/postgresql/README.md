# PostgreSQL

## 收藏

#### [PostgreSQL 博文、资料、学习笔记、系列教程汇总](https://www.cnblogs.com/aixing/p/14918624.html)

#### [PostgreSQL-with 子句实现递归](https://zhuanlan.zhihu.com/p/159555056)

#### PG 复制表结构

```sql
create table src ( like dst including all );
```

#### PG 进程管理

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
where current_query !~* '(<idle>|<insufficient privilege>|SET application_name|SET search_path|SET SESSION search_path|SHOW search_path)'
order by lap desc;

-- 杀事务
select pg_cancel_backend(pid);
select pg_terminate_backend(pid);

--查询具体表的执行情况
select * from pg_stat_activity where query ~ '表名';
```

#### PG 修改序列起始值

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

#### PG 数组截取

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

#### PG 查看表结构信息

> [PostgreSQL 中系统表](https://blog.csdn.net/qq_33459369/article/details/124021543)

```sql
select table_schema, table_name, table_type, table_comment, column_name, column_type, column_order, column_comment, column_nullable, column_default
from (
    select
        pg_namespace.nspname          as table_schema,
        pg_class.relname              as table_name,
        (case pg_class.relkind when 'r' then 't' when 'v' then 'v' end) as table_type,
        obj_description(pg_class.oid) as table_comment,
        pg_attribute.attname          as column_name,
        concat_ws('', pg_type.typname, substring(format_type(pg_attribute.atttypid, pg_attribute.atttypmod) from '\(.*\)')) as column_type,
        pg_attribute.attnum           as column_order,
        pg_description.description    as column_comment,
        not pg_attribute.attnotnull   as column_nullable,
        pg_get_expr(pg_attrdef.adbin, pg_attrdef.adrelid) as column_default
    from      pg_catalog.pg_namespace
    left join pg_catalog.pg_class       on pg_class.relnamespace = pg_namespace.oid
    left join pg_catalog.pg_attribute   on pg_attribute.attrelid = pg_class.oid
    left join pg_catalog.pg_attrdef     on pg_attrdef.adrelid    = pg_attribute.attrelid and pg_attrdef.adnum = pg_attribute.attnum
    left join pg_catalog.pg_type        on pg_type.oid           = pg_attribute.atttypid
    left join pg_catalog.pg_description on pg_description.objoid = pg_attribute.attrelid and pg_description.objsubid = pg_attribute.attnum
    where pg_namespace.nspname not in ('information_schema', 'pg_catalog', 'pg_toast') and pg_class.relkind in ('r', 'v') and pg_attribute.attnum > 0 and pg_attribute.attisdropped = false
    order by table_schema, table_name, column_order
) t
```

#### PG 查看索引信息

```sql
select table_schema, table_name, index_name, index_def
from (
    select schemaname as table_schema, tablename as table_name, indexname as index_name, indexdef as index_def
    from pg_indexes
    where schemaname not in ('information_schema', 'pg_catalog', 'pg_toast')
    order by table_schema, table_name, index_name
) t
```

#### PG 查看分区信息

```sql
select table_schema， table_name, partition_name
from (
    select
        n.nspname as table_schema,
        c.relname as table_name,
        p.relname as partition_name
    from pg_namespace n
    join pg_class c on c.relnamespace = n.oid
    join (select * from pg_partition where pg_partition.parttype = 'r') r on r.parentid = c.oid
    join (select * from pg_partition where pg_partition.parttype = 'p') p on p.parentid = r.parentid
    where n.nspname not in ('information_schema', 'pg_catalog', 'pg_toast')
    order by table_schema, table_name, partition_name
) t
```

#### 查看表空间信息

```sql
select spcname as tablespace_name, pg_size_pretty(pg_tablespace_size(spcname)) as tablespace_size
from pg_tablespace
```

#### PG 拿到所有 ID 放在 IN 中

> `''` 表示转义一个单引号

```sql
select array_to_string(array_agg('''' || id ||''''), ', ') from a;
```

#### PG `WITH` 递归

```sql
-- 向下递归
with recursive tree AS (
    select m.id, m.parent_id, m.code, m.name from menu m where m.id = #{id}
    union all
    select m.id, m.parent_id, m.code, m.name from menu m join tree on m.parent_id = tree.id
)
select * from tree;
-- 向上递归
with recursive tree AS (
    select m.id, m.parent_id, m.code, m.name from menu m where m.id = #{id}
    union all
    select m.id, m.parent_id, m.code, m.name from menu m join tree on m.id = tree.parent_id
)
select * from tree;
```
