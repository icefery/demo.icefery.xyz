# Oracle

## 收藏

#### Oracle 创建一个表如果不指定表空间是不是就是用默认的 `system` 表空间

> https://bbs.csdn.net/topics/390217815

创建表格时，如果不指定所用的表空间，安装以下顺序使用用户的默认表空间：

1. 创建用户时指定的默认表空间。

    ```sql
    create user identified by oracle default tablespace users;
    ```

2. 创建数据库是指定的默认表空间。可以通过以下 语句修改：

    ```sql
    alter database default tablespace users;
    ```

    如果创建用户时，不指定默认的 `tablespace`，则此用户则会以数据库的默认表空间作为默认表空间。

    ```sql
    -- 查看用户的默认表空间
    select username, default_tablespace from dba_users;
    ```

3. 如果 1) 和 2) 都没有设置，才会使用 `system` 表空间。

#### Oracle 查询历史 SQL 记录

```sql
select * from v$sqlarea where sql_text like '%select * from user%'
```

#### [Oracle 根据当前时间获取最近 5 年，最近 6 个月，最近 30 天的时间](https://blog.csdn.net/maple_fix/article/details/74926226)

### 查看所有表

```sql
select * from all_tables where table_name like '%%'
```

### 查询表结构信息

```sql
select
    tc.owner      as "schema_name",
    tc.table_name as "table_name",
    (case
        when tc.table_type = 'TABLE' then 't'
        when tc.table_type = 'VIEW'  then 'v'
        else tc.table_type
    end)                                                                         as "table_type",
    tc.comments                                                                  as "table_comment",
    c.column_name                                                                as "column_name",
    row_number() over(partition by tc.owner, tc.table_name order by c.column_id) as "column_order",
    (case
        when c.data_type in ('CHAR', 'VARCHAR2', 'NCHAR', 'NVARCHAR2')                            then c.data_type || '(' || to_char(c.char_length) || ')'
        when c.data_type = 'NUMBER' and c.data_precision is not null and c.data_scale is not null then c.data_type || '(' || to_char(c.data_precision) || ',' || to_char(c.data_scale) || ')'
        when c.data_type = 'NUMBER' and c.data_precision is not null                              then c.data_type || '(' || to_char(c.data_precision) || ')'
        else c.data_type
        end)                                                     as "column_type",
    (case when c.nullable = 'Y' then 1 else 0 end)               as "column_nullable",
    c.data_default                                               as "column_default",
    (case when pk_col.column_name is not null then 1 else 0 end) as "column_primary_key",
    cc.comments                                                  as "column_comment"
from all_tab_comments tc
join all_tab_columns  c  on c.owner  = tc.owner and c.table_name  = tc.table_name
join all_col_comments cc on cc.owner = c.owner  and cc.table_name = c.table_name and cc.column_name = c.column_name
left join (
    select acc.owner, acc.table_name, acc.column_name
    from all_cons_columns acc
    join all_constraints ac on ac.owner = acc.owner and ac.constraint_name = acc.constraint_name
    where ac.constraint_type = 'P'
) pk_col on pk_col.owner = tc.owner and pk_col.table_name = tc.table_name and pk_col.column_name = c.column_name
where 1 = 1
and tc.owner not in ('SYS', 'SYSTEM')
and tc.table_type in ('TABLE', 'VIEW')
and c.data_type not in ('ROWID', 'UROWID')
order by tc.owner, tc.table_name, c.column_id
```

### [Oracle EBS 初始化用户密码](https://www.cnblogs.com/toowang/p/6019886.html)

### 查看视图定义

```sql
select dbms_metadata.get_ddl('VIEW', '<VIEW_NAME>') from dual
```

```sql
select * from all_views where owner not in ('SYS', 'SYSTEM')
```

### 查看所有对象

```sql
select
    owner                                     as "对象归属",
    object_type                               as "对象类型",
    object_name                               as "对象名称",
    object_id                                 as "对象标识",
    status                                    as "对象状态",
    to_char(created, 'yyyy-mm-dd hh24:mi:ss') as "对象创建时间"
from sys.all_objects
where owner not in ('sys', 'system')
order by owner, object_type, object_name
```

### 查看会话信息

```sql
select
    s.sid,
    s.serial#,
    s.username,
    t.sql_text,
    (case
        when last_call_et >= 86400 then floor(last_call_et / 86400) || ' days ' || floor(mod(last_call_et, 86400) / 3600) || ' hours ' || floor(mod(mod(last_call_et, 86400), 3600) / 60) || ' minutes ' || mod(mod(last_call_et, 86400), 60) || ' seconds'
        when last_call_et >= 3600  then floor(last_call_et / 3600) || ' hours ' || floor(mod(last_call_et, 3600) / 60) || ' minutes ' || mod(last_call_et, 60) || ' seconds'
        else floor(last_call_et / 60) || ' minutes ' || mod(last_call_et, 60) || ' seconds'
    end) as "elapsed time"
from v$session s
join v$sqltext t on s.sql_id = t.sql_id
where 1 = 1
    and s.last_call_et > 1 * 60 * 60
    and t.sql_text like 'insert %'
```
