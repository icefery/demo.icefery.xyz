# Flink CDC

> [![](https://img.shields.io/github/stars/apache/flink-cdc.svg)](https://github.com/apache/flink-cdc)

## 一、MySQL CDC

### 1.1 建库建表

```sql
create database demo;

create table demo.t_product (
    id    bigint,
    dt    datetime,
    name  varchar(64),
    price decimal(38,2),
    primary key(id)
);

insert into demo.t_product values
    (1, current_timestamp, 'A', 99.99),
    (2, current_timestamp, 'B', 88.88);
```

### 1.2 启用 CDC

```sql
show variables like '%log_bin%';
show variables like '%binlog_format%';
show variables like '%server_id%';
```

```sql
create user 'cdc_readonly'@'%' identified by 'cdc_readonly';
grant select, replication slave, replication client on *.* to 'cdc_readonly'@'%';
flush privileges;
```

### 1.3 `flink-sql-connector-mysql-cdc`

```sql
create table t_product_source (
    database_name  string           metadata from 'database_name' virtual,
    table_name     string           metadata from 'table_name'    virtual,
    op_ts          timestamp_ltz(3) metadata from 'op_ts'         virtual,
    id    bigint,
    dt    timestamp(0),
    name  string,
    price decimal(38,2),
    primary key(id) not enforced
) with (
    'connector'     = 'mysql-cdc',
    'hostname'      = '192.168.8.101',
    'port'          = '3306',
    'username'      = 'cdc_readonly',
    'password'      = 'cdc_readonly',
    'database-name' = 'demo',
    'table-name'    = 't_product',
    'server-time-zone' = 'Asia/Shanghai',
    'scan.startup.mode' = 'initial'
);

set 'sql-client.execution.result-mode' = 'tableau';

select * from t_product_source;
```

<br/><br/>

## 二、PostgreSQL CDC

### 2.1 建库建表

```sql
create database demo;
```

```sql
create table public.t_product (
    id    int8,
    dt    timestamp(0),
    name  varchar(64),
    price numeric(38,2),
    primary key(id)
);

insert into public.t_product values
    (1, current_timestamp, 'A', 99.99),
    (2, current_timestamp, 'B', 88.88);
```

### 2.2 启用 CDC

```sql
-- 查看 WAL 级别
show wal_level;
-- 修改 WAL 级别
alter system set wal_level = 'logical';
-- 重启
```

```sql
-- 查看复制槽
select * from pg_replication_slots;
-- 删除复制槽
select pg_drop_replication_slot('t_product');
```

```sql
-- 新建具备 login 和 replication 权限的角色
create role cdc_readonly with login replication password 'cdc_readonly';

-- 授权已有的角色 login 和 replication 权限
-- alter role cdc_readonly login replication;

-- 授权模式 usage 权限
grant usage on schema public to cdc_readonly;

-- 授权表 select 权限
grant select on table public.t_product to cdc_readonly;
```

### 2.3 `flink-sql-connector-postgres-cdc`

```sql
set execution.checkpointing.interval = 3s;
set sql-client.execution.result-mode = tableau;

create table t_product_source (
    database_name  string           metadata from 'database_name' virtual,
    schema_name    string           metadata from 'schema_name'   virtual,
    table_name     string           metadata from 'table_name'    virtual,
    op_ts          timestamp_ltz(3) metadata from 'op_ts'         virtual,
    id    bigint,
    dt    timestamp(0),
    name  string,
    price decimal(38,2),
    primary key(id) not enforced
) with (
    'connector'     = 'postgres-cdc',
    'hostname'      = '192.168.31.101',
    'port'          = '5432',
    'username'      = 'cdc_readonly',
    'password'      = 'cdc_readonly',
    'database-name' = 'demo',
    'schema-name'   = 'public',
    'table-name'    = 't_product',
    'slot.name' = 't_product',
    'decoding.plugin.name' = 'pgoutput',
    'scan.incremental.snapshot.enabled' = 'true',
    'scan.startup.mode' = 'initial'
);

select * from t_product_source;
```

<br/><br/>

## 三、SQLServer CDC

> -   https://github.com/apache/flink-cdc/blob/master/docs/content/docs/connectors/cdc-connectors/sqlserver-cdc.md
> -   https://learn.microsoft.com/zh-cn/sql/linux/sql-server-linux-configure-environment-variables

### 3.1 建库建表

```sql
create database demo;

use demo;

create table dbo.t_product (
    id    bigint,
    dt    datetime2,
    name  varchar(64),
    price decimal(38,2),
    primary key(id)
);

insert into dbo.t_product values
    (1, current_timestamp, 'A', 99.99),
    (2, current_timestamp, 'B', 88.88);
```

### 3.2 启用 CDC

> 启用成功后，会在当前库的 `cdc` 模式下生成如下六张表:
>
> -   `captured_columns`
> -   `change_tables`
> -   `ddl_history`
> -   `index_columns`
> -   `lsn_time_mapping`
> -   `dbo_t_product_CT`(启动 CDC 的用户表)

```sql
use demo;

-- 1. 验证 SQLServer Agent 是否运行
select *  from master.dbo.sysprocesses WHERE program_name = 'SQLAgent - Generic Refresher';

-- 2. 启用库级别 CDC
exec sys.sp_cdc_enable_db;
select is_cdc_enabled from sys.databases where name = 'demo';

-- 3. 启用表级别 CDC
exec sys.sp_cdc_enable_table @source_schema = 'dbo', @source_name = 't_product', @role_name = null,;
select is_tracked_by_cdc from sys.tables where name = 't_product';

-- 4. 验证用户是否有权访问 CDC 表
exec sys.sp_cdc_help_change_data_capture;
```

### 3.3 `flink-sql-connector-sqlserver-cdc`

```sql
set execution.checkpointing.interval = 3s;
set sql-client.execution.result-mode = tableau;

create table t_product_source (
    database_name  string           metadata from 'database_name' virtual,
    schema_name    string           metadata from 'schema_name'   virtual,
    table_name     string           metadata from 'table_name'    virtual,
    op_ts          timestamp_ltz(3) metadata from 'op_ts'         virtual,
    id    bigint,
    dt    timestamp(0),
    name  string,
    price decimal(38,2),
    primary key(id) not enforced
) with (
    'connector'     = 'sqlserver-cdc',
    'hostname'      = '192.168.31.101',
    'port'          = '1433',
    'username'      = 'sa',
    'password'      = 'sa@sqlserver:0000',
    'database-name' = 'demo',
    'table-name'    = 'dbo.t_product',
    'server-time-zone' = 'Asia/Shanghai',
    'scan.startup.mode' = 'initial'
);

select * from t_product_source;
```

<br/><br/>

## 四、Oracle CDC

### 4.1 建库建表

```sql
create table T_PRODUCT (
    ID NUMBER(19,0),
    DT DATE,
    NAME VARCHAR2(64),
    PRICE NUMBER(38,2),
    PRIMARY KEY(ID)
);

INSERT ALL
INTO T_PRODUCT VALUES(1, SYSDATE, 'A', 99.99)
INTO T_PRODUCT VALUES(2, SYSDATE, 'B', 88.88)
SELECT * FROM DUAL;
```

### 4.2 启用 CDC

### 4.3 `flink-sql-connector-oracle-cdc`

```sql
set execution.checkpointing.interval = 3s;
set sql-client.execution.result-mode = tableau;

create table t_product_source (
    database_name string           metadata from 'database_name' virtual,
    schema_name   string           metadata from 'schema_name'   virtual,
    table_name    string           metadata from 'table_name'    virtual,
    op_ts         timestamp_ltz(3) metadata from 'op_ts'         virtual,
    ID    bigint,
    DT    timestamp,
    NAME  string,
    PRICE decimal(38,2),
    primary key(ID) not enforced
) with (
    'connector'     = 'oracle-cdc',
    'hostname'      = '',
    'url'           = '',
    'username'      = 'cdc_readonly',
    'password'      = 'cdc_readonly',
    'database-name' = '',
    'schema-name'   = '',
    'table-name'    = 'T_PRODUCT',
    'scan.startup.mode' = 'initial'
);

select * from t_product_source;
```

<br/><br/>

## 五、MongoDB CDC

### 5.1 建库建集合

```javascript
use dmeo;

db.t_product.insertMany([
  { dt: new Date(), name: 'A', price: 99.99 },
  { dt: new Date(), name: 'B', price: 88.88 }
]);
```

### 5.2 启用 CDC

```javascript
use admin;

db.createRole({
  role: 'cdc_readonly',
  privileges: [
    {
      resource: { db: '', collection: '' },
      actions: ['splitVector', 'listDatabases', 'listCollections', 'collStats', 'find', 'changeStream']
    }
  ],
  roles: [{ role: 'read', db: 'config' }]
});

db.createUser({
  user: 'cdc_readonly',
  pwd: 'cdc_readonly',
  roles: [{ role: 'cdc_readonly', db: 'admin' }]
});
```

### 5.3 `flink-sql-connector-mongodb-cdc`

```sql
set execution.checkpointing.interval = 3s;
set sql-client.execution.result-mode = tableau;

create table t_product_source (
    database_name   string           metadata from 'database_name'   virtual,
    collection_name string           metadata from 'collection_name' virtual,
    op_ts           timestamp_ltz(3) metadata from 'op_ts'           virtual,
    _id   string,
    dt    timestamp(0),
    name  string,
    price decimal(38,2),
    primary key(_id) not enforced
) with (
  'connector'  = 'mongodb-cdc',
  'hosts'      = '192.168.8.101:27017',
  'username'   = 'cdc_readonly',
  'password'   = 'cdc_readonly',
  'database'   = 'demo',
  'collection' = 't_product',
  'scan.startup.mode' = 'initial'
);

select * from t_product_source;
```
