### 安装

-   https://mirrors.tuna.tsinghua.edu.cn/apache/kafka/3.0.0/
-   https://kafka.apache.org/quickstart

### KRaft 配置

-   `config/kraft/README.md`

    ![](__image__/1ec389e73ffd43679565f4b125160fd2.png)

-   `config/kraft/server.properties`

    ![](__image__/0251b1ff07274bb7b0795128977ef0b4.png)

-   生成集群 ID

    ```shell
    bin/kafka-storage.sh random-uuid > uuid
    ```

    ![](__image__/1e44a45a84134d3a9d4461372c67335e.png)

-   格式化存储目录

    ```shell
    bin/kafka-storage.sh format -t `cat uuid` -c config/kraft/server.properties
    ```

### 启动

-   启动

    ```shell
    bin/kafka-server-start.sh -daemon config/kraft/server.properties
    ```

-   创建主题

    ```shell
    bin/kafka-topics.sh --create --topic foo --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092
    ```
