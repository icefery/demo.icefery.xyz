# Docker

## 安装

### 卸载

```bash
sudo apt-get purge docker-ce docker-ce-cli containerd.io docker-compose-plugin

sudo rm -rf /var/lib/docker

sudo rm -rf /var/lib/containerd
```

### 安装

```bash
curl -fsSL https://get.docker.com | bash
```

### 镜像源

```bash
echo '{ "registry-mirrors": ["https://uwk49ut2.mirror.aliyuncs.com"] }' > /etc/docker/daemon.json

systemctl daemon-reload

systemctl restart docker
```

## 收藏

- [关于 Dockerfile 中 echo 的用法](https://www.jianshu.com/p/7c7c6c2c6f6b#comments)

- [docker 挂载数据卷](https://www.cnblogs.com/kerwincui/p/12544603.html)

## 常见问题

### `Warning: Stopping docker.service, but it can still be activated by: docker.socket`

> https://blog.csdn.net/weixin_43885975/article/details/117809901

> 这是因为除了 `docker.service` 单元文件，还有一个 `docker.socket` 单元文件用于套接字激活。该警告意味着：如果你试图连接到 `docker.socket`，而 Docker 服务没有运行，系统将自动启动 docker。

- 方法一：`rm /lib/systemd/system/docker.socket`
- 方法二：`sudo systemctl stop docker.socket`
