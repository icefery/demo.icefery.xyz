## 版本

|                        |              |
| ---------------------- | ------------ |
| `jdk`                  | `11`         |
| `spring-boot`          | `2.6.11`     |
| `spring-cloud`         | `2021.0.4`   |
| `spring-cloud-alibaba` | `2021.0.4.0` |
| `mybatis-plus`         | `3.5.2`      |

## 启动

### 初始化数据库

-   `sql/sql.sql`
-   `https://github.com/seata/seata/blob/develop/script/client/at/db/mysql.sql`

### 启动服务

### 测试

-   提交

    ```shell
    curl http://127.0.0.1:8081/purchase/commit
    ```

-   回滚

    ```shell
    curl http://127.0.0.1:8081/purchase/rollback
    ```
