# Sqoop

## Export

#### Hive 到 PostgreSQL

```shell
sqoop export \
  --connect 'jdbc:postgresql://192.168.8.101:5432/warehouse' \
  --username 'postgres' \
  --password 'postgres' \
  --columns 'col_1, col_2, col_3' \
  --hcatalog-database 'stg' \
  --hcatalog-table 'stg_sys_user' \
  --table 'stg_sys_user' \
  --update-mode allowinsert \
  --m 1 \
  -- --schema 'stg'
```
