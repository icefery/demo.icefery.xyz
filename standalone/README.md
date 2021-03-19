```
elasticsearch
  |-- docker.elastic.co/elasticsearch/elasticsearch:7.11.1
  |-- docker.elastic.co/kibana/kibana:7.11.1
  |-- docker.elastic.co/logstash/logstash:7.11.1
minio
  |-- minio/minio:latest
mongodb
  |-- mongo:4.4
mysql
  |-- mysql:8.0
nacos
  |-- nacos/nacos-server:1.4.1
nginx
  |-- nginx:1.19
  |-- cym1102/nginxwebui:latest
portainer
  |-- portainer/portainer:latest
postgresql
  |-- postgres:13.2
rabbitmq
  |-- rabbitmq:3.8-management
redis
  |-- redis:6.2
rocketmq
  |-- foxiswho/rocketmq:4.8.0
sqlserver
  |-- mcr.microsoft.com/mssql/server:2019-latest
zookeeper
  |-- zookeeper:3.6
```



| Compose       | Service           | Image                                         | Tag            | Port                                          |
| ------------- | ----------------- | --------------------------------------------- | :------------- | --------------------------------------------- |
| elasticsearch | elasticsearch     | docker.elastic.co/elasticsearch/elasticsearch | 7.11.1         | 9200:9200                                     |
|               | kibana            | docker.elastic.co/kibana/kibana               | 7.11.1         | 5601:5601                                     |
|               | logstash          | docker.elastic.co/logstash/logstash           | 7.11.1         | 无                                            |
| minio         | minio             | minio/minio                                   | latest         | 9000                                          |
| mongodb       | mongodb           | mongo                                         | 4.4            | 27017:27017                                   |
| mysql         | mysql             | mysql                                         | 8.0            | 3306                                          |
| nacos         | nacos             | nacos/nacos-server                            | 1.4.1          | 8848:8848                                     |
| nginx         | nginx             | nginx                                         | 1.19           | 80:80                                         |
|               | nginx-web-ui      | cym1102/nginxwebui                            | latest         | 8080:8080                                     |
| portainer     | portainer         | portainer/portainer                           | latest         | 9000:9000                                     |
| postgresql    | postgresql        | postgres                                      | 13.2           | 5432:5432                                     |
| rabb          | rabbitmq          | rabbitmq                                      | 3.8-management | 5672:5672<br />15672:15672                    |
| redis         | redis             | redis                                         | 6.2            | 6379:6379                                     |
|               | redis-insight     | redislabs/redisinsight                        | latest         | 8001:8001                                     |
| rocketmq      | rocketmq-namesrv  | foxiswho/rocketmq                             | 4.8.0          | 9876:9876                                     |
|               | rocketmq-broker-a | foxiswho/rocketmq                             | 4.8.0          | 10909:10909<br />10911:10911<br />10912:10912 |
| sqlserver     | sqlserver         | mcr.microsoft.com/mssql/server                | 2019-latest    | 1433:1433                                     |
| zookeeper     | zookeeper         | zookeeper                                     | 3.6            | 2181:2181                                     |

