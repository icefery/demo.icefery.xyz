# openGauss

## 收藏

### 修改表空间

```sql
-- 普通表
alter table <table_name> set tablespace <table_space>;

-- 分区表
alter table <table_name> move partition <table_partition> tablespace <table_space>;

-- 索引
alter index <index_name> set tablespace <table_space>;
```

### 兼容性

> https://docs-opengauss.osinfra.cn/zh/docs/5.0.0/docs/SQLReference/CREATE-DATABASE.html

| 兼容性 | 兼容的数据库类型 | 说明                                                                                              |
| :----- | :--------------- | :------------------------------------------------------------------------------------------------ |
| `A`    | Oracle           | 数据库会将空字符串作为 `NULL` 处理，数据类型 `DATE` 会被替换为 `TIMESTAMP(0) WITHOUT TIME ZONE`。 |
| `B`    | MySQL            | 将字符串转换成整数时，如何输入不合法， `B` 兼容性会将输入转换为 `0`，而其它兼容性则会报错。       |
| `C`    | Teradata         |                                                                                                   |
| `PG`   | PostgreSQL       | `PG` 兼容性下，`CHAR` 和 `VARCHAR` 已字符为计数单位，其它兼容性以字节为计数单位。                 |

```sql
create database demo encoding = 'UTF8' dbcompatibility = 'PG';
```

### 压缩

> -   https://docs-opengauss.osinfra.cn/zh/docs/5.0.0/docs/SQLReference/CREATE-TABLE.html
> -   https://docs-opengauss.osinfra.cn/zh/docs/5.0.0/docs/SQLReference/列存表支持的数据类型.html

```sql
create table ods.ods_dwd_urs_claim (

)
with (
    orientation=column,
    compression=yes
);
```

### 认证

> https://blog.csdn.net/weixin_48456383/article/details/124019202

-   `5.x` 版本：使用 [`org.postgresql:postgresql:42.7.1`](https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.1/postgresql-42.7.1.jar) 驱动会报错 `Invalid or unsupported by client SCRAM mechanisms` ；使用 [`org.opengauss:opengauss-jdbc:5.1.0`](https://repo1.maven.org/maven2/org/opengauss/opengauss-jdbc/5.1.0/opengauss-jdbc-5.1.0.jar) 正常。
-   `3.x` 版本：使用两种驱动都能正常连接。
