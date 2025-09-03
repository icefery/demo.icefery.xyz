# 云原生

## 收藏

#### 利用容器中的 mysql 客户端执行 sql 文件或文本

```shell
docker exec -i mysql mysql -uroot -proot -e 'show databases';

docker exec -i mysql mysql -uroot -proot < script/seatunnel_server_mysql.sql
# cat script/seatunnel_server_mysql.sql | docker exec -i mysql mysql -uroot -proot
```

#### [没错，数据库确实应该放入 K8s 里！](https://mp.weixin.qq.com/s/IDsF_f7ZnB19jEu8ZtO-Nw)

#### [开源云原生平台 KubeSphere 与 Rainbond 对比](https://mp.weixin.qq.com/s/VIxJNlJHQu91T7ASXg7sAQ)

#### [openEBS](https://weiliang-ms.github.io/wl-awesome/2.容器/k8s/storage/OpenEBS.html)

#### [docker 容器操作宿主机执行命令](https://www.ewbang.com/community/article/details/961692037.html)
