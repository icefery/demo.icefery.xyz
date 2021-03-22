## SQL
#### mysql

| Service | Image | Tag  | Port      |
| ------- | ----- | ---- | --------- |
| mysql   | mysql | 8.0  | 3306:3306 |


#### sqlserver

| Service   | Image                          | Tag         | Port      |
| --------- | ------------------------------ | ----------- | --------- |
| sqlserver | mcr.microsoft.com/mssql/server | 2019-latest | 1433:1433 |

#### portgresql

| Service    | Image    | Tag  | Port      |
| ---------- | -------- | ---- | --------- |
| portgresql | portgres | 13.2 | 5432:5432 |





## NoSQL

#### redis

| Service       | Image                  | Tag    | Port      |
| ------------- | ---------------------- | ------ | --------- |
| redis         | redis                  | 6.2    | 6379:6379 |
| redis-insight | redislabs/redisinsight | latest | 8001:8001 |


#### mongodb

| Service | Image | Tag  | Port        |
| ------- | ----- | ---- | ----------- |
| mongodb | mongo | 4.4  | 27017:27017 |





## ELK

#### elasticsearch

| Service       | Image                                         | Tag    | Port      |
| ------------- | --------------------------------------------- | ------ | --------- |
| elasticsearch | docker.elastic.co/elasticsearch/elasticsearch | 7.11.1 | 9200:9200 |
| kibana        | docker.elastic.co/kibana/kibana               | 7.11.1 | 5601:5601 |
| logstash      | docker.elastic.co/logstash/logstash           | 7.11.1 |           |





## MQ

#### rabbitmq

| Service  | Image    | Tag            | Port                       |
| -------- | -------- | -------------- | -------------------------- |
| rabbitmq | rabbitmq | 3.8-management | 5672:5672<br />15672:15672 |


####  rocketmq

| Service           | Image             | Tag   | Port                                          |
| ----------------- | ----------------- | ----- | --------------------------------------------- |
| rocketmq-namesrv  | foxiswho/rocketmq | 4.8.0 | 9876:9876                                     |
| rocketmq-broker-a | foxiswho/rocketmq | 4.8.0 | 10909:10909<br />10911:10911<br />10912:10912 |






## OSS

#### minio

| Service | Image       | Tag    | Port                               |
| ------- | ----------- | ------ | ---------------------------------- |
| minio   | minio/minio | latest | <font color="red">9000</font>:9000 |





## Registry / Config

#### nacos

| Service | Image              | Tag   | Port       |
| ------- | ------------------ | ----- | ---------- |
| nacos   | nacos/nacos-server | 1.4.1 | 8848:8848  |

#### zookeeper


| Service   | Image     | Tag  | Port |
| --------- | --------- | ---- | ---- |
| zookeeper | zookeeper | 3.6  | 2181 |





## Web Server

#### nginx

| Service      | Image              | Tag    | Port                                  |
| ------------ | ------------------ | ------ | ------------------------------------- |
| nginx        | nginx              | 1.19   | 80:80                                 |
| nginx-web-ui | cym1102/nginxwebui | latest | <font color="orange">8080</font>:8080 |





## Other

#### portainer

| Service   | Image               | Tag    | Port                               |
| --------- | ------------------- | ------ | ---------------------------------- |
| portainer | portainer/portainer | latest | <font color="red">9000</font>:9000 |


