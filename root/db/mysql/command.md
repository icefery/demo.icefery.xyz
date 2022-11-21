#### 查询 MySQL 提供的所有存储引擎

```sql
SHOW ENGINES;
```

#### 查看 MySQL 当前默认的存储引擎

```sql
SHOW VARIABLES LIKE '%storage_engine%';
```

#### 查看表的存储引擎

```sql
SHOW TABLE STATUS LIKE 'table_name';
```

#### 查看 MySQL 事务隔离级别

```sql
SELECT @@tx_isolation;
```
