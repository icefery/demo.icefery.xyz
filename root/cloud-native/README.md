# 云原生

## 收藏

#### 利用容器中的 mysql 客户端执行 sql 文件或文本

```shell
docker exec -i mysql mysql -uroot -proot -e 'show databases';

docker exec -i mysql mysql -uroot -proot < script/seatunnel_server_mysql.sql
# cat script/seatunnel_server_mysql.sql | docker exec -i mysql mysql -uroot -proot
```
