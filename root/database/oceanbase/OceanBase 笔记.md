### docker 安装 oceanbase

-   `docker-compose.yml`

    ```yaml
    version: '3'
    services:
      oceanbase:
        image: oceanbase/obce-mini:latest
        container_name: oceanbase
        hostname: oceanbase
        environment:
          - OB_ROOT_PASSWORD=root
          - OB_TENANT_NAME=icefery
        ports:
          - 2881:2881
        volumes:
          - ./oceanbase/root/boot/boot-tmp.yaml:/root/boot/boot-tmp.yaml
          - ./oceanbase/root/ob/:/root/ob/
    networks:
      default:
        name: local
        external: true
    ```

-   `/root/boot/boot-tmp.yaml`

    > `system_memory`为系统预留内存，可以调小，但 `memory_limit` 至少为 8G 时 OBServer 才能正常启动

    ```yaml
    #FROM https://gitee.com/oceanbase/obdeploy/blob/master/example/mini-local-example.yaml
    oceanbase-ce:
      servers:
        - 127.0.0.1
      global:
        home_path: @OB_HOME_PATH@ # default: /root/ob
        devname: lo
        mysql_port: @OB_MYSQL_PORT@ # default: 2881
        rpc_port: @OB_RPC_PORT@ # default: 2882
        zone: zone1
        cluster_id: 1
        memory_limit: 8G
        system_memory: 1G
        stack_size: 512K
        cpu_count: 16
        cache_wash_threshold: 1G
        __min_full_resource_pool_memory: 268435456
        workers_per_cpu_quota: 10
        schema_history_expire_time: 1d
        net_thread_count: 4
        sys_bkgd_migration_retry_num: 3
        minor_freeze_times: 10
        enable_separate_sys_clog: 0
        enable_merge_by_turn: FALSE
        enable_auto_leader_switch: FALSE
        enable_one_phase_commit: FALSE
        weak_read_version_refresh_interval: 5s
        trace_log_slow_query_watermark: 10s
        large_query_threshold: 1s
        clog_sync_time_warn_threshold: 2000ms
        syslog_io_bandwidth_limit: 10M
        enable_sql_audit: FALSE
        enable_perf_event: FALSE
        clog_max_unconfirmed_log_count: 5000
        autoinc_cache_refresh_interval: 86400s
        cpu_quota_concurrency: 2
        datafile_size: 5G
        syslog_level: WARN
        enable_syslog_recycle: TRUE
        max_syslog_file_count: 2
        enable_early_lock_release: false tenant=all
        default_compress_func: lz4_1.0
        root_password: @OB_ROOT_PASSWORD@ # default: null

    ```

-   连接

    ```shell
    # 连接 sys 租户
    ob-mysql sys

    # 连接默认租户的 root y
    ob-mysql root
    ```

### Oracle 兼容性暂不支持的功能

-   不支持 `LONG` 和 `LONG RAW` 数据类型
-   `SELECT` 语句中不支持 `pivot` 和 `unpivot` 子句
-   对于触发器，不支持在视图上创建触发器；不支持对触发器进行`DISABLE`和`ENABLE`操作
-   PL 中暂不支持条件编译
-   数据库约束中，不支持 `UNIQUE` 约束的 `DISABLE` 操作；不支持添加外键约束的 `DISABLE` 和 `ENABLE`；不支持级联中的 `SET NULL`
-   暂不支持 SPLIT、MERGE、EXCHANGE 和 TRUNCATE 分区
-   不支持函数、位图和反向等索引类型
-   不支持数据库链接
-   不支持 `WITH CHECK OPTION` 子句
-   暂不支持删除审计相关的各类视图
-   对于备份恢复功能，不支持组级别的备份；不支持租户级指定备份的手动删除；不支持备份数据的有效性验证；不支持数据库和表级别的备份恢复
-   对于 SQL 引擎，暂不支持估算器、执行计划隔离、表达式统计存储（ESS）和近似查询处理功能

### MySQL 兼容性暂不支持的功能

-   暂不支持空间数据类型和 JSON 数据类型。
-   不支持 `SELECT … FOR SHARE …` 语法。
-   不支持空间分析函数、JSON 函数和性能模式函数。
-   对于备份恢复功能，不支持数据库和表级的备份恢复；不支持备份数据的有效性验证。
-   对于优化器，查看执行计划的命令不支持使用 `SHOW WARNINGS` 显示额外的信息；不支持执行 `ANALYZE TABLE` 查询数据字典中表存储有关列值的直方图统计信息。

### 基本概念

|            |                                                                                            |     |
| ---------- | ------------------------------------------------------------------------------------------ | --- |
| 地域       | Region 指一个地域或者城市（例如杭州、上海、深圳等），一个 Region 包含一个或者多个 Zone     |     |
| 可用区     | 一个 OceanBase 集群，由若干个可用区（Zone）组成，通常由一个机房内的若干服务器组成一个 Zone |     |
| 资源池     | 一个租户拥有若干个资源池，一个资源池只能属于一个租户                                       |     |
| OBServer   | 运行 OBServer 进程的物理机。一台物理机上可以部署一个或者多个 OBServer                      |     |
| OBProxy    | 兼容 Oracle/MySQL 的客户端的代理服务                                                       |     |
| RootServer | 主控服务器。主要进行集群管理、数据分布和副本管理                                           |     |

### 数据分布

-   Hash 分区

    > 通常用于给定分区键的点查询，例如按照用户 ID 来分区。Hash 分区通常能消除热点查询

    ```sql
    CREATE TABLE t1 (
        c1 INT,
        c2 INT
    ) PARTITION BY HASH(c1) PARTITIONS 5
    ```

-   Range 分区

    > 通常用于对分区键需要按照范围的查询。例如通过按照时间字段进行范围分区，还有价格区间等一些分区方式

    ```sql
    CREATE TABLE t2 (
        c1 NUMBER,
        c2 NUMBER
    ) PARTITION BY RANGE(c1) (
    	PARTITION p0 VALUES LESS THAN(100),
    	PARTITION p1 VALUES LESS THAN(500),
    	PARTITION p2 VALUES LESS THAN(MAXVALUE)
    );
    ```

-   List 分区

    > 主要用于枚举类型

    ```sql
    CREATE TABLE t3 (
        c1 NUMBER,
        c2 NUMBER
    ) PARTITION BY RANGE(c1) (
    	PARTITION p0 VALUES IN (1,2,3),
    	PARTITION p1 VALUES IN (5, 6),
    	PARTITION p2 VALUES IN (DEFAULT)
    );
    ```

### SQL 引擎

#### 基于规则的查询改写

-   视图合并

    ```sql
    SELECT t1.c1, v.c1
    FROM t1, (SELECT t2.c1, t3.c2 FROM t2, t3 WHERE t2.c1 = t3.c1) v
    WHERE t1.c2 = v.c2;
    ```

    ```sql
    SELECT t1.c1, t2.c1
    FROM t1, t2, t3
    WHERE t2.c1 = t3.c1 AND t1.c2 = t3.c2;
    ```

-   子查询展开

    ```sql
    SELECT * FROM t1 WHERE t1.c1 IN  (SELECT t2.c1 FROM t2)
    ```

    ```sql
    SELECT t1.* FROM t1, t2 WHERE t1.c1 = t2.c1;
    ```

-   `ANY`/`ALL` 使用 `MAX/MIN` 改写

    ```sql
    SELECT c1 FROM t1 WHERE c1 > ANY(SELECT c1 FROM t2);
    ```

    ```sql
    SELECT c1 FROM t1 WHERE c1 > (SELECT MIN(c1) FROM t2);
    ```

-   外联接消除

    ```sql
    SELECT t1.c1, t2.c2 FROM t1 LEFT JOIN t2 ON t1.c2 = t2.c2 WHERE t2.c2 > 5;
    ```

    ```sql
    SELECT t1.c1, t2.c2 FROM t1 INNER JOIN t2 ON t1.c2 = t2.c2 WHERE t2.c2 > 5;
    ```

-   `HAVING`条件消除

    > 如果查询中没有聚集操作和 `GROUP BY`，则 `HAVING` 可以合并到 `WHERE` 条件中，并将 `HAVING` 条件删除， 从而可以将 `HAVING` 条件在 `WHERE` 条件中统一管理，并做进一步优化

    ```sql
    SELECT * FROM t1, t2 WHERE t1.c1 = t2.c1 HAVING t1.c2 > 1;
    ```

    ```sql
    SELECT * FROM t1, t2 WHERE t1.c1 = t2.c1 AND t1.c2 > 1;
    ```

-   等价关系推导

    > 通过 `a ＝ b AND a > 1` 可以推导出 `a ＝ b AND a > 1 AND b > 1`， 如果 `b` 列上有索引，且 `b > 1` 在该索引选择率很低，则可以大大提升访问 `b` 列所在表的性能

-   恒真/假消除

    > 对于 `WHERE 0 > 1 AND c1 ＝ 3`，由于 `0 > 1` 使得 `AND` 恒假， 所以该 SQL 不用执行，可直接返回，从而加快查询的执行

-   冗余排序消除

    > `ORDER BY` 列中存在 `WHERE` 中有单值条件的列，该列排序可删除

    ```sql
    SELECT * FROM t1 WHERE c2 = 5 ORDER BY c1, c2, c3;
    ```

    ```sql
    SELECT * FROM t1 WHERE c2 = 5 ORDER BY c1, c3;
    ```

    > 如果本层查询有 `ORDER BY` 但是没有 `LIMIT`，且本层查询位于父查询的集合操作中，则 `ORDER BY` 可消除。因为对两个有序的集合做 `UNION` 操作，其结果是乱序的。但是如果 `ORDER BY` 中有 `LIMIT`，则语义是取最大/最小的 N 个，此时不能消除 `ORDER BY`，否则有语义错误

    ```sql
    (SELECT c1,c2 FROM t1 ORDER BY c1) UNION (SELECT c3,c4 FROM t2 ORDER BY c3)；
    ```

    ```sql
    (SELECT c1,c2 FROM t1) UNION (SELECT c3,c4 FROM t2)；
    ```

-   LIMIT 下压

    > `LIMIT` 下压改写是指将 `LIMIT` 下降到子查询中

    ```sql
    SELECT * FROM (SELECT * FROM t1 ORDER BY c1) a LIMIT 1;
    ```

    ```sql
    SELECT * FROM (SELECT * FROM t1 ORDER BY c1 LIMIT 1) a LIMIT 1;
    ```

-   DISTINCT 消除

    > 如果 Select Item 中只包含常量，则可以消除 `DISTINCT`，并加上 `LIMIT 1`

    > 如果 Select Item 中包含确保唯一性约束的列，则 `DISTINCT` 能够被消除

-   MIN/MAX 改写

    > 当 `MIN`/`MAX` 函数中的参数为索引前缀列，且不含 `GROUP BY` 时，可将该 `scalar aggregate` 转换为走索引扫描 1 行的情况

    ```sql
    SELECT MIN(c2) FROM t1;
    ```

    ```sql
    SELECT MIN(c2) FROM (SELECT c2 FROM t2 ORDER BY c2 LIMIT 1) AS t;
    ```

    > 如果 `SELECT MIN`/`SELECT MAX` 的参数为常量，而且包含 `GROUP BY`，可以将 `MIN`/`MAX` 改为常量，从而减少 `MIN`/`MAX` 的计算开销

    ```sql
    SELECT MAX(1) FROM t1 GROUP BY c1;
    ```

    ```sql
    SELECT 1 FROM t1 GROUP BY c1;
    ```

    > 如果 `SELECT MIN`/`SELECT MAX` 的参数为常量，而且不含 `GROUP BY`，可以按照如下示例进行改写，从而走索引只需扫描 1 行

    ```sql
    SELECT MAX(1) FROM t1;
    ```

    ```sql
    SELECT MAX(t.a) FROM (SELECT 1 AS a FROM t1 LIMIT 1) t;
    ```

#### 基于代价的查询改写

-   OR 展开

    > `LNNVL `用于某个语句的 `WHERE` 子句中的条件，如果条件为 `true` 就返回 `false`；如果条件为 UNKNOWN 或者 `false` 就返回 `true`。该函数不能用于复合条件如 `AND`、`OR`、`BETWEEN`中

    ```sql
    SELECT * FROM t1 WHERE t1.a = 1 OR t1.b = 1;
    ```

    ```sql
    SELECT * FROM t1 WHERE t1.a = 1
    UNION ALL
    SELECT * FROM t1.b = 1 AND LNNVL(t1.a = 1);
    ```

    作用：

    -   允许每个分支使用不同的索引来加速查询
    -   允许每个分支使用不同的联接算法来加速查询，避免使用笛卡尔联接
    -   允许每个分支分别消除排序，更加快速的获取 TOP-K 结果

### FQA

-   使用 OceanBase 数据库在开发中要特别注意什么

    -   表建好后，主键不能更改。如果需要修改，只能删表重建
    -   列类型修改有较大限制。Varchar 长度只能由短变长，不能由长变短。
    -   索引生效时间较长，建议在建表时将索引语句一并纳入。
    -   大数据量导入需要特别关注内存的使用情况。
    -   mysql-connector-java 的版本建议使用 5.1.30 及以上。
    -   如果一个连接超过 15 分钟空闲，服务端会主动断开，在使用连接池的时候需要设置一个连接最大的空闲时间。例如，Druid 的 minEvictableIdleTimeMillis 小于 15 分钟
