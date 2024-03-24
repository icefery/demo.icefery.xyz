# ETCD

## 基本操作

### 增(`put`)

```shell
etcdctl put a "hello"
```

### 查(`get`)

-   单个查找
    ```shell
    etcdctl get a
    ```
-   范围查找

    ```shell
    # 查找范围为 [a1, a3)
    etcdctl get a1 a3
    ```

-   前缀查找

    ```shell
    # 查找所有以 /a/b 开头的 key
    etcdctl get /a/b/ --prefix
    ```

-   限制数量

    ```shell
    # 最多显示两个
    etcdctl get /a/b/ --prefix --limit 2
    ```

### 删(`del`)

-   范围删除
    ```shell
    # 删除范围为 [a1, a3)
    etcdctl del a1 a3
    ```

### 历史变动(`watch`)

```shell
etcdctl watch a
```

### 租约(`lease`)

> `lease` 类似于 Redis 中的 `TTL`。

-   授予租约

    ```shell
    etcdctl lease grant 100
    # lease 694d84b3b6d6c614 granted with TTL(100s)
    ```

-   将租约添加到 key4 上

    ```shell
    etcdctl put key4 "hello" --lease=694d84b3b6d6c614
    ```

-   查看租约剩余时间

    ```shell
    etcdctl lease timetolive 694d84b3b6d6c614
    ```
