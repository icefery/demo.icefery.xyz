## 版本

|                |          |
| :------------- | :------- |
| `jdk`          | `17`     |
| `spring-boot`  | `2.7.6`  |
| `mybatis-plus` | `3.5.2`  |
| `redisson`     | `3.18.0` |

## 开始

### 生成数据

-   `MyTest.generate()`

    ```
    totalCount=1000000 batchSize=1000 batchCount=1000 cost=8703
    ```

### 初始化布隆过滤器

-   `curl http://127.0.0.1:8080/bloom-filter/rebuild`

    ```
    filterName=user:id_list filterFactor=0.01 listSize=1000000 rebuildCost=134633
    ```
