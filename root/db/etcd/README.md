# ETCD

## 基本操作

### 增(`put`)

```bash
etcdctl put a "hello"
```

### 查(`get`)

- 单个查找
  ```bash
  etcdctl get a
  ```
- 范围查找

  ```bash
  # 查找范围为 [a1, a3)
  etcdctl get a1 a3
  ```

- 前缀查找

  ```bash
  # 查找所有以 /a/b 开头的 key
  etcdctl get /a/b/ --prefix
  ```

- 限制数量

  ```bash
  # 最多显示两个
  etcdctl get /a/b/ --prefix --limit 2
  ```

### 删(`del`)

- 范围删除
  ```bash
  # 删除范围为 [a1, a3)
  etcdctl del a1 a3
  ```

### 历史变动(`watch`)

```bash
etcdctl watch a
```

### 租约(`lease`)

> `lease` 类似于 Redis 中的 `TTL`。

- 授予租约

  ```bash
  etcdctl lease grant 100
  # lease 694d84b3b6d6c614 granted with TTL(100s)
  ```

- 将租约添加到 key4 上

  ```bash
  etcdctl put key4 "hello" --lease=694d84b3b6d6c614
  ```

- 查看租约剩余时间

  ```bash
  etcdctl lease timetolive 694d84b3b6d6c614
  ```
