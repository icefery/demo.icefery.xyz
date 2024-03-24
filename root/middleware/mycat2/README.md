# MyCAT2

## 安装

```shell
wget http://dl.mycat.org.cn/2.0/install-template/mycat2-install-template-1.21.zip
wget http://dl.mycat.org.cn/2.0/1.22-release/mycat2-1.22-release-jar-with-dependencies-2022-10-13.jar
wget http://dl.mycat.org.cn/2.0/install-template/wrapper-linux-aarch64-64

unzip -d /opt/env mycat2-install-template-1.21.zip
cp mycat2-1.22-release-jar-with-dependencies-2022-10-13.jar /opt/env/mycat/lib
cp wrapper-linux-aarch64-64 /opt/env/mycat/bin

chmod +x -R /opt/env/mycat/bin
```

## 启动

1. 修改 `prototype` 数据源连接信息

    ```shell
    vim conf/datasources/prototypeDs.datasource.json
    ```

2. 启动

    ```shell
    cd /opt/env/mycat

    bin/mycat start
    ```

3. 连接

    ```shell
    mysql -h192.192.192.101 -P8066 -uroot -p123456
    ```

4. 重置配置

    > 直接在 MyCAT 的客户端执行。

    ```sql
    /*+ mycat:resetConfig{} */;
    ```

## 读写分离配置

1. 添加数据源

    ```sql
    /*+ mycat:createDataSource{ "name":"ds0", "url":"jdbc:mysql://192.192.192.101:3306/demo", "user":"root", "password":"root" } */;
    /*+ mycat:createDataSource{ "name":"ds1", "url":"jdbc:mysql://192.192.192.101:3307/demo", "user":"root", "password":"root" } */;
    /*+ mycat:createDataSource{ "name":"ds2", "url":"jdbc:mysql://192.192.192.101:3308/demo", "user":"root", "password":"root" } */;
    /*+ mycat:showDataSources{} */;
    ```

2. 添加集群

    ```sql
    /*! mycat:createCluster{"name":"prototype","masters":["ds0"],"replicas":["ds1", "ds2"]} */;
    /*+ mycat:showClusters{} */;
    ```

3. 创建虚拟库和虚拟表

    > MyCAT 建表时使用 `auto_increment` 默认使用 MyCAT 的雪花算法生成全局序列号。

    ```sql
    create database demo;

    use demo;

    create table t_user (
      id       bigint      not null auto_increment,
      username varchar(64) not null,
      primary key(id)
    );
    ```

## 分库分表配置

### 数据源配置

```sql
/*+ mycat:createDataSource{ "name":"ds0", "url":"jdbc:mysql://192.192.192.101:3306", "user":"root", "password":"root" } */;
/*+ mycat:createDataSource{ "name":"ds1", "url":"jdbc:mysql://192.192.192.101:3307", "user":"root", "password":"root" } */;

/*! mycat:createCluster{"name":"c0","masters":["ds0"],"replicas":[]} */;
/*! mycat:createCluster{"name":"c1","masters":["ds1"],"replicas":[]} */;

/*+ mycat:showDataSources{} */;
/*+ mycat:showClusters{} */;
```

### 广播表

```sql
create table t_dict (
    id         bigint      not null auto_increment,
    dict_key   varchar(64) not null,
    dict_value varchar(64) not null,
    primary key(id)
) broadcast;
```

### 分片表

```sql
create table t_order (
   id         bigint      not null auto_increment,
   order_code varchar(64) not null,
   primary key(id)
)
dbpartition by mod_hash(order_code) dbpartitions 2
tbpartition by mod_hash(id)         tbpartitions 3;
```

```sql
create table t_order_item (
    id            bigint        not null,
    order_code    varchar(64)   not null,
    product_price decimal(10,2) not null,
    product_count bigint        not null,
    primary key(id)
)
dbpartition by mod_hash(order_code) dbpartitions 2
tbpartition by mod_hash(id)         tbpartitions 3;
```

### ER 关系

```sql
/*+ mycat:showErGroup{} */;
```

### 分片规则

#### `MOD_HASH`

> 如果分片值是字符串则先对字符串进行 hash 转换为数值类型。

-   分库键和分表键是同键

    ```java
    分表下标 = 分片值 % (分库数量 * 分表数量)
    分库下标 = 分表下标 / 分表数量
    ```

-   分库键和分表键是不同键

    ```java
    分表下标 = 分片值 % 分表数量
    分库下标 = 分片值 % 分库数量
    ```
