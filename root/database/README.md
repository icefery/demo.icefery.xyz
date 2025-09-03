# 数据库

## 收藏

-   [sql 里面如何引用列的别名](https://segmentfault.com/q/1010000000622075)

-   [Greenplum 6 已合并到 PostgreSQL 9.3 版本 - 比上一代 GP 提升：8 倍读，195 倍更新、删除 - 另有大量 PG 新特性](https://developer.aliyun.com/article/698154)

-   [Oracle 异常代码](https://blog.csdn.net/qq_32445015/article/details/81224497)

-   https://neo4j.com/developer/cypher/intro-cypher/

-   [关于 varchar 的总结](https://www.cnblogs.com/xinruyi/p/11403151.html)

-   [Oracle 查询语句练习（二）](https://blog.csdn.net/qq_32445015/article/details/81137365)

-   [如何在 SQL 中使用 JOIN 执行 UPDATE 语句？](https://qa.1r1g.com/sf/ask/90533131/)

-   [后端程序员必备：书写高质量 SQL 的 30 条建议](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247486461&idx=1&sn=60a22279196d084cc398936fe3b37772&chksm=cea24436f9d5cd20a4fa0e907590f3e700d7378b3f608d7b33bb52cfb96f503b7ccb65a1deed&token=1987003517&lang=zh_CN%2523rd)

-   [腾讯面试：一条 SQL 语句执行得很慢的原因有哪些？---不看后悔系列](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485185&idx=1&sn=66ef08b4ab6af5757792223a83fc0d45&chksm=cea248caf9d5c1dc72ec8a281ec16aa3ec3e8066dbb252e27362438a26c33fbe842b0e0adf47&token=79317275&lang=zh_CN%2523rd)

-   [一条 SQL 语句在 MySQL 中如何执行的](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485097&idx=1&sn=84c89da477b1338bdf3e9fcd65514ac1&chksm=cea24962f9d5c074d8d3ff1ab04ee8f0d6486e3d015cfd783503685986485c11738ccb542ba7&token=79317275&lang=zh_CN%2523rd)

-   [MySQL 高性能优化规范建议,速度收藏](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485117&idx=1&sn=92361755b7c3de488b415ec4c5f46d73&chksm=cea24976f9d5c060babe50c3747616cce63df5d50947903a262704988143c2eeb4069ae45420&token=79317275&lang=zh_CN%2523rd)

-   [SqlServer 基础之(触发器)](https://www.cnblogs.com/wangprince2017/p/7827091.html)

-   [Sql Server 中的 DBCC 命令详细介绍](https://www.jb51.net/article/61450.htm)

-   [PostgreSQL 之窗口函数的用法](https://www.cnblogs.com/funnyzpc/p/9311281.html)

-   [高性能 MySQL——Count(1) OR Count(\*)？](https://zhuanlan.zhihu.com/p/28397595)

-   [嵌入式数据库(Java): Derby，SQLite，H2](https://www.jianshu.com/p/3f34b1c584c3)

-   [看一遍就理解：MVCC 原理详解](https://mp.weixin.qq.com/s?__biz=Mzg3NzU5NTIwNg==&mid=2247495277&idx=1&sn=a1812febb4246f824ce54d778f672025&chksm=cf223144f855b8528ad6cce707dc3a1b4d387817bd751dfab4f79dda90c6640f9763d25f3f33&scene=132#wechat_redirect)

-   [雪花算法生成的 id 是全球唯一吗？](https://www.zhihu.com/question/447384625)

-   [雪花算法(Snowflake) - 改进版](https://blog.csdn.net/ciap37959/article/details/100619920)

## 展开 JSON 对象数组

-   MySQL `JSON_TABLE` 函数

    ```sql
    select
        t.col_1,
        t.col_2
    from json_table(
        '[{"col_1": 1, "col_2": "aa"}, {"col_1": 11, "col_2": "bb"}]',
        '$[*]' columns (
            col_1 bigint      path '$.col_1',
            col_2 varchar(64) path '$.col_2'
        )
    ) t
    ```

-   MySQL 通用

    ```sql
    with
    recursive seq as (
        select 0 as i
        union all
        select i + 1 from seq where i < 999
    )
    select
        cast(json_unquote(json_extract(row_list, concat('$[', i, '].col_1'))) as signed)      as col_1,
        cast(json_unquote(json_extract(row_list, concat('$[', i, '].col_2'))) as varchar(64)) as col_2
    from (
        select '[{"col_1": 1, "col_2": "aa"}, {"col_1": 11, "col_2": "bb"}]' as row_list
    ) t
    join seq on i < json_length(row_list)
    ```

-   PostgreSQL `jsonb_array_elements`

    ```sql
    select
        (t->>'col_1')::bigint      as col_1,
        (t->>'col_2')::varchar(64) as col_2
    from jsonb_array_elements('[{"col_1": 1, "col_2": "aa"}, {"col_1": 11, "col_2": "bb"}]'::jsonb) t;
    ```

-   DuckDB

    ```sql
    select info.*
    from (
        select unnest(json_transform('[{"col_1": 1, "col_2": "aa"}, {"col_1": 11, "col_2": "bb"}]'::json, '[{"col_1": "bigint", "col_2": "varchar(64)" }]'::json)) as info
    ) t
    ```
