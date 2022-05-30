# Oracle

## 收藏

- Oracle 创建一个表如果不指定表空间是不是就是用默认的 `system` 表空间

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
