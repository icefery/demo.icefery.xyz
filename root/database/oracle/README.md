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
