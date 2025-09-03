# Canal

## 快速开始

### 安装

```shell
wget https://github.com/alibaba/canal/releases/download/canal-1.1.6/canal.deployer-1.1.6.tar.gz

mkdir -p /opt/env/canal

tar -zxvf canal.deployer-1.1.6.tar.gz -C /opt/env/canal
```

### 配置

-   `conf/canal.properties`

    ```properties
    # ...
    canal.serverMode = kafka
    kafka.bootstrap.servers = 127.0.0.1:9092
    ```

-   `conf/example/instance.properties`

    ```properties
    # ...
    canal.instance.master.address = 127.0.0.1:3306
    canal.instance.dbUsername = root
    canal.instance.dbPassword = root
    canal.mq.topic = canal
    ```

### 启动

```shell
rm -rf conf/example/h2.mv.db

rm -rf conf/example/meta.dat

bin/startup.sh
```

### 查看日志

```shell
tail logs/canal/canal.log -f -n 1000

tail logs/example/example.log -f -n 1000
```

### 消息格式

-   `TRUNCATE`

    ```json
    {
      "data": null,
      "database": "demo",
      "es": 1672403650000,
      "id": 1,
      "isDdl": true,
      "mysqlType": null,
      "old": null,
      "pkNames": null,
      "sql": "truncate table t_user",
      "sqlType": null,
      "table": "t_user",
      "ts": 1672403650485,
      "type": "TRUNCATE"
    }
    ```

-   `INSERT`

    ```json
    {
      "data": [{ "id": "1", "username": "u1", "email": "u1@gmail.com" }],
      "database": "demo",
      "es": 1672403822000,
      "id": 2,
      "isDdl": false,
      "mysqlType": { "id": "bigint", "username": "varchar(64)", "email": "varchar(64)" },
      "old": null,
      "pkNames": ["id"],
      "sql": "",
      "sqlType": { "id": -5, "username": 12, "email": 12 },
      "table": "t_user",
      "ts": 1672403822125,
      "type": "INSERT"
    }
    ```

-   `UPDATE`

    ```json
    {
      "data": [{ "id": "1", "username": "u1", "email": "icefery@163.com" }],
      "database": "demo",
      "es": 1672403904000,
      "id": 3,
      "isDdl": false,
      "mysqlType": { "id": "bigint", "username": "varchar(64)", "email": "varchar(64)" },
      "old": [{ "email": "u1@gmail.com" }],
      "pkNames": ["id"],
      "sql": "",
      "sqlType": { "id": -5, "username": 12, "email": 12 },
      "table": "t_user",
      "ts": 1672403904314,
      "type": "UPDATE"
    }
    ```

-   `DELETE`

    ```json
    {
      "data": [{ "id": "1", "username": "u1", "email": "icefery@163.com" }],
      "database": "demo",
      "es": 1672403941000,
      "id": 4,
      "isDdl": false,
      "mysqlType": { "id": "bigint", "username": "varchar(64)", "email": "varchar(64)" },
      "old": null,
      "pkNames": ["id"],
      "sql": "",
      "sqlType": { "id": -5, "username": 12, "email": 12 },
      "table": "t_user",
      "ts": 1672403941524,
      "type": "DELETE"
    }
    ```
