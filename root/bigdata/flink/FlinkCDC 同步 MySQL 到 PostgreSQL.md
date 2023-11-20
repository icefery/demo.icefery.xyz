## 快速开始

### 手动编译 FlinkCDC 连接器

```shell
git clone https://github.com/ververica/flink-cdc-connectors.git
cd flink-cdc-connectors
git checkout release-2.4.2
mvn clean install -DskipTests

ls flink-connector-mysql-cdc/target/flink-connector-mysql-cdc-2.4.2.jar
ls flink-sql-connector-mysql-cdc/target/flink-sql-connector-mysql-cdc-2.4.2.jar
```

### SQL API

#### 建表

```sql
-- mysql
create database demo;
create table demo.t1 (
    id bigint primary key auto_increment,
    dt datetime
);

-- postgresql
create table public.t1 (
    id bigint primary key,
    dt timestamp
);
```

#### 下载依赖

```shell
wget -P ./lib https://repo1.maven.org/maven2/com/ververica/flink-sql-connector-mysql-cdc/2.4.2/flink-sql-connector-mysql-cdc-2.4.2.jar
wget -P ./lib https://repo1.maven.org/maven2/org/apache/flink/flink-connector-jdbc/3.1.0-1.17/flink-connector-jdbc-3.1.0-1.17.jar
wget -P ./lib https://repo1.maven.org/maven2/org/postgresql/postgresql/42.6.0/postgresql-42.6.0.jar

./bin/start-cluster.sh

./bin/sql-client.sh
```

#### 提交任务

```sql
create table t1_source (
    id bigint primary key not enforced,
    dt timestamp
) with (
    'connector' = 'mysql-cdc',
    'hostname' = '192.168.31.101',
    'port' = '3306',
    'username' = 'root',
    'password' = 'root',
    'database-name' = 'demo',
    'table-name' = 't1'
);

create table t1_sink (
    id bigint primary key not enforced,
    dt timestamp
) with (
    'connector' = 'jdbc',
    'url' = 'jdbc:postgresql://192.168.31.101:5432/demo',
    'username' = 'postgres',
    'password' = 'postgres',
    'table-name' = 't1'
);

insert into t1_sink select * from t1_source;
```
