# DB2

## 收藏

#### 查看表结构信息

数据类型：

-   `BIGINT`
-   `BLOB`
-   `CHARACTER`
-   `CLOB`
-   `DATE`
-   `DB2SECURITYLABEL`
-   `DECIMAL`
-   `DOUBLE`
-   `INTEGER`
-   `REAL`
-   `SMALLINT`
-   `TIMESTAMP`
-   `VARCHAR`
-   `XML`

```sql
select "table_schema", "table_name", "table_type", "table_comment", "column_name", "column_type", "column_order", "column_default", "column_nullable"
from (
    select
        t.tabschema   as "table_schema",
        t.tabname     as "table_name",
        lower(t.type) as "table_type",
        t.remarks     as "table_comment",
        c.colname     as "column_name",
        (case
            when c.typename = 'BIGINT'           then c.typename
            when c.typename = 'BLOB'             then c.typename
            when c.typename = 'CHARACTER'        then c.typename || '(' || c.length || ')'
            when c.typename = 'CLOB'             then c.typename
            when c.typename = 'DATE'             then c.typename
            when c.typename = 'DB2SECURITYLABEL' then c.typename
            when c.typename = 'DECIMAL'          then c.typename || '(' || c.length || ',' || c.scale || ')'
            when c.typename = 'DOUBLE'           then c.typename
            when c.typename = 'INTEGER'          then c.typename
            when c.typename = 'REAL'             then c.typename
            when c.typename = 'SMALLINT'         then c.typename
            when c.typename = 'TIMESTAMP'        then c.typename || '(' || c.length || ')'
            when c.typename = 'VARCHAR'          then c.typename || '(' || c.length || ')'
            when c.typename = 'XML'              then c.typename
        end)        as "column_type",
        c.colno + 1 as "column_order",
        c.default   as "column_default",
        (case when c.nulls = 'Y' then 1 when c.nulls = 'N' then 0 end) as "column_nullable"
    from syscat.tables t
    join syscat.columns c on c.tabschema = t.tabschema and c.tabname = t.tabname
    where t.tabschema not in ('SYSCAT', 'SYSIBM', 'SYSIBM', 'SYSPUBLC', 'SYSSTAT', 'SYSTOOLS') and t.type IN ('T', 'V')
    order by "table_schema", "table_type", "column_order"
) t
```
