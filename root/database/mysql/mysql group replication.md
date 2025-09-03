## MGR 介绍

MySQL 是目前最流行的开源关系型数据库，国内金融行业也开始全面使用，其中 MySQL 5.7.17 提出的 MGR（MySQL Group Replication）既可以很好的保证一致性又可以自动切换，具备故障检测功能、支持多节点写入，MGR 是一项被普遍看好的技术。

MGR 是 MySQL 自带的一个插件，可以灵活部署 。MySQL MGR 集群是多个 MySQL Server 节点共同组成的分布式集群，每个 Server 都有完整的副本。

## MGR 优点

-   强一致性。基于原生复制和 Paxos 协议的组复制技术，并以插件的方式提供，提供一致的数据安全保证。

-   高容错性。只要不是大多数节点坏掉就要可以继续工作，有自动检测机制，当不同节点产生资源争用冲突时，不会出现错误，按照先到者优先原则进行处理，并且内置了自动化脑裂防护机制。

-   高扩展性。节点的新增和移除都是自动的，新节点加入后，会自动从其它节点上同步状态，直到新节点和其它节点保持一致，如果某节点被移除了，其它节点自动更新组信息，自动维护新的组信息。

-   高灵活性。有单主模式和多主模式，单主模式下，会自动选主，所有操作都在主节点上进行；多主模式下，所有 Server 都可以同时处理更新操作。

## MGR 使用约束

-   仅支持 InnoDB 引擎，并且每张表一定要有一个主键，用于做 Write Set 的冲突检测。

-   必须打开 GTID 特性，二进制日志格式必须设置为 ROW，用于选主与 Write Set；主从状态信息存在于表中。

-   MGR 不支持大事务，事务大小最好不超过 143MB，当事务过大，无法在 5s 的时间内通过网络在组成员之间复制消息，则可能会怀疑成员失败了，然后将其驱逐出局。

-   目前一个 MGR 集群最多支持 9 个节点。

-   不支持 Save Point 特性，无法做到全局间的约束检测与部分事务回滚。

-   二进制日志不支持 Binlog Event Checksum。

## MGR 适用场景

-   金融交易、重要数据存储、对主从一致性要求高的场景。

-   核心数据总量未过亿。

-   读多写少的应用场景，如互联网电商。

## 单主 MGR 集群搭建

### 1 docker-compose

#### 1.1 目录结构

```
mgr/
|-- docker-compose.yaml
|-- mysql101/
    |-- etc/
        |-- mysql/
            |-- conf.d/
                |-- my.cnf
|-- mysql102/
    |-- etc/
        |-- mysql/
            |-- conf.d/
                |-- my.cnf
|-- mysql103/
    |-- etc/
        |-- mysql/
            |-- conf.d/
                |-- my.cnf
```

#### 1.2 `docker-compose.yaml`

```yaml
version: '3'

services:
  mysql101:
    image: mysql:8.0.28
    container_name: mysql101
    hostname: mysql101
    volumes:
      - ./mysql101/etc/mysql/conf.d/:/etc/mysql/conf.d/
    environment:
      - TZ=Asia/Shanghai
      - MYSQL_ROOT_PASSWORD=root
  mysql102:
    image: mysql:8.0.28
    container_name: mysql102
    hostname: mysql102
    volumes:
      - ./mysql102/etc/mysql/conf.d/:/etc/mysql/conf.d/
    environment:
      - TZ=Asia/Shanghai
      - MYSQL_ROOT_PASSWORD=root
  mysql103:
    image: mysql:8.0.28
    container_name: mysql103
    hostname: mysql103
    volumes:
      - ./mysql103/etc/mysql/conf.d/:/etc/mysql/conf.d/
    environment:
      - TZ=Asia/Shanghai
      - MYSQL_ROOT_PASSWORD=root

networks:
  default:
    external: true
    name: local
```

#### 1.3 配置文件

-   mysql101 配置。

    ```shell
    mkdir -p mysql101/etc/mysql/conf.d

    cat <<-EOF >mysql101/etc/mysql/conf.d/my.cnf
    [mysqld]
    # custom
    bind-address                  = 0.0.0.0
    lower-case-table-names        = 1
    default-time-zone             = +8:00
    default-authentication-plugin = mysql_native_password

    # mgr
    disabled_storage_engines="MyISAM,BLACKHOLE,FEDERATED,ARCHIVE,MEMORY"
    server_id=101
    gtid_mode=ON
    enforce_gtid_consistency=ON
    binlog_checksum=NONE

    plugin_load_add='group_replication.so'
    loose-group_replication_group_name="aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
    loose-group_replication_start_on_boot=off
    loose-group_replication_local_address="mysql101:33061"
    loose-group_replication_group_seeds="mysql101:33061,mysql102:33061,mysql103:33061"
    loose-group_replication_bootstrap_group=off
    EOF
    ```

-   mysql102、mysql103 修改 `server_id` 和 `loose-group_replication_local_address`。

#### 1.4 启动

```shell
docker network create local

docker-compose up -d

# 查看日志
docker logs -f mysql101
```

### 2 组件集群

#### 2.1 主节点

```shell
SET GLOBAL group_replication_bootstrap_group=ON;
START GROUP_REPLICATION;
SET GLOBAL group_replication_bootstrap_group=OFF;
```

#### 2.2 从节点

```shell
RESET MASTER;
START GROUP_REPLICATION;
```

#### 2.3 验证

```shell
SELECT * FROM performance_schema.replication_group_members;
```

### 3 故障转移测试

#### 3.1 选主策略

-   优先低版本节点
-   如果版本一样，优先权重大的节点
-   如果版本与权重一样，按照 `MEMBER_ID` 的顺序选主

#### 3.2 注意事项

当只剩一个节点时，集群节点数少于 Paxos n/2+1 的规则，导致整体 MGR 集群失效，最后一个节点无法重新选举，同时日志也不会有任何新内容产生。

#### 3.3 整体集群恢复

```shell
START GROUP_REPLICATION;
```

## 单主模式和多主模式

在单主模式下，组复制具有自动选主功能，每次只有一个 Server 成功接受更新。单写模式 Group 内只有一个节点只可以读。对于 Group 的部署，需要先跑 Primary 节点，然后再跑其他节点，并把这些节点加进 Group。其它的节点就会自动同步 Primary 节点上的变化，然后将自己设置为只读模式。当 Primary 节点发生意外宕机或者下线，在满足多节点存活的情况下，Group 内部发起选举，选出下一个可用的读节点，提升为 Primary 节点。Primary 选举根据 Group 内剩下存活节点的 UUID 按字典升序来选择，然后选择排在最前面的节点作为新的 Primary 节点。

在多主模式下，所有的 Server 成员都可以同时接受更新。Group 内的所有成员机器都是 Primary 节点，同时可以进行读写操作，并且数据都是一致的。

### 1 运行时切换为多主模式

#### 1.1 启动多主模式

```sql
-- 停止组复制
STOP GROUP_REPLICATION;

-- 启用多主模式
SET global_group_replication_single_primary_mode=OFF;

-- 启用条件检查
SET global_group_replication_enforce_update_everywhere_checks=ON;
```

多主的约束更为严格，不符合要求的直接拒绝。

-   不支持外键的级联操作
-   不支持 Serializable（串行化）

#### 1.2 引导组复制

同单组模式。各节点加入集群后，所有节点都是 Primary 节点。

### 2 应用层双活和数据库双活、应用层双活和数据库单活

#### 2.1 MGR 集群多活架构

基于 MGR 的多活特性，数据的写入可以在多个节点之间复制，实现数据墙强一致性需求，并且在节点间通信出现延迟的情况下，会自动实现服务降级。对于此类方案，我们可以采用同机房多写，同城异机房只读的方案。

#### 2.2 双主模式的多活

两个节点均可以写入数据，可以实现跨机房的数据复制，延迟较低，在业务层需要做隔离，在故障发生时能够快速切换到同机房的 Slave 节点。此方案对于两个 IDC 机房的场景中较为实用，但是机房多活的场景不适合。

#### 2.3 业务交叉的双活方案
