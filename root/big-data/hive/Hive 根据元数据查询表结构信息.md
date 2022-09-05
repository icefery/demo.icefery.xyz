```sql
SELECT
    dbs.`name`                  AS db_name,
    tbls.tbl_name               AS table_name,
    table_params.param_value    AS table_comment,
    columns_v2.column_name      AS column_name,
    columns_v2.type_name        AS column_type,
    columns_v2.`comment`        AS column_comment,
    partition_keys.pkey_name    AS partition_column_name,
    partition_keys.pkey_type    AS partition_column_type,
    partition_keys.pkey_comment AS partition_column_comment
FROM dbs
    LEFT JOIN tbls           ON tbls.db_id = dbs.db_id
    LEFT JOIN table_params   ON table_params.tbl_id = tbls.tbl_id AND table_params.param_key = 'comment'
    LEFT JOIN sds            ON sds.sd_id = tbls.sd_id
    LEFT JOIN columns_v2     ON columns_v2.cd_id = sds.cd_id
    LEFT JOIN partition_keys ON partition_keys.tbl_id = tbls.tbl_id
ORDER BY dbs.`name`, tbls.tbl_name, columns_v2.integer_idx
```
