## ShardingSphere JDBC 读写分离

## 常见问题

#### [Springboot with JPA, readwrite-splitting always on write-ds #15629](https://github.com/apache/shardingsphere/issues/15629)

> 这个问题的根源是 Spring Data JPA 默认所有方法都使用了事务，当然查询是只读事务，而 ShardingSphere 判断使用了事务就走主库。
