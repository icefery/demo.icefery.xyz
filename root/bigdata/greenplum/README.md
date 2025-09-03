# Greenplum

## 收藏

#### [Greenplum 目录结构、配置文件和环境变量](https://blog.csdn.net/murkey/article/details/105714858)

#### [Greenplum6 JDBC insert 性能媲美 MySQL](https://segmentfault.com/a/1190000022131758)

#### [greenplum 常用命令&运维命令](https://blog.csdn.net/weixin_45403933/article/details/131786060)

## 元数据

### Greenplum6 分布策略和分区策略

> 关于处理 `parkind = 'h'` 的哈希分区情况，因为 Greenplum6 可能不支持哈希分区或该功能的表现形式不同，通常该值不被使用。但是，为了提供更完整的查询，可以在 `case` 表达式中添加一个分支以涵盖这种情况。

```sql
select
    pg_namespace.nspname as schema_name,
    pg_class.relname     as table_name,
    (case
        when gp_distribution_policy.policytype = 'r'                                          then 'disteibuted replicated'
        when gp_distribution_policy.policytype = 'p' and gp_distribution_policy.distkey = ''  then 'distributed randomly'
        when gp_distribution_policy.policytype = 'p' and gp_distribution_policy.distkey != '' then 'disteibuted by (' || array_to_string(array(select attname from pg_attribute where attrelid = pg_class.oid and attnum = any(gp_distribution_policy.distkey)), ', ') || ')'
    end) as distribution_policy,
    (
        (case
            when pg_partition.parkind = 'r' then 'partition by range ('
            when pg_partition.parkind = 'l' then 'partition by list ('
            when pg_partition.parkind = 'h' then 'partition by hash ('
        end)
        ||
        array_to_string(array(select attname from pg_attribute where attrelid = pg_class.oid and attnum = any(pg_partition.paratts)), ', ')
        ||
        ')'
    ) as partition_policy
from pg_catalog.pg_namespace
left join pg_catalog.pg_class               on pg_class.relnamespace           = pg_namespace.oid
left join pg_catalog.gp_distribution_policy on gp_distribution_policy.localoid = pg_class.oid
left join pg_catalog.pg_partition           on pg_partition.parrelid           = pg_class.oid
where 1 = 1
    and pg_namespace.nspname not in ('information_schema', 'pg_catalog', 'pg_toast', 'pg_aoseg', 'gp_bitmapindex', 'gp_toolkit')
    and pg_class.relkind = 'r'
    and not exists (select 1 from pg_catalog.pg_inherits where inhrelid = pg_class.oid)
order by schema_name, table_name
```
