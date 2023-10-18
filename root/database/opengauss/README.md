# openGauss

## 收藏

#### 修改表空间

```sql
-- 普通表
alter table <table_name> set tablespace <table_space>;

-- 分区表
alter table <table_name> move partition <table_partition> tablespace <table_space>;

-- 索引
alter index <index_name> set tablespace <table_space>;
```
