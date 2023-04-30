# Docker

## 快速开始

> [Install Docker Engine on Ubuntu](https://docs.docker.com/engine/install/ubuntu/#install-using-the-convenience-script)

## 收藏

- [关于 Dockerfile 中 echo 的用法](https://www.jianshu.com/p/7c7c6c2c6f6b#comments)

- [docker 挂载数据卷](https://www.cnblogs.com/kerwincui/p/12544603.html)

- `Warning: Stopping docker.service, but it can still be activated by: docker.socket`

  > https://blog.csdn.net/weixin_43885975/article/details/117809901

  > 这是因为除了 `docker.service` 单元文件，还有一个 `docker.socket` 单元文件用于套接字激活。该警告意味着：如果你试图连接到 `docker.socket`，而 Docker 服务没有运行，系统将自动启动 docker。

  ```shell
  sudo systemctl disable docker.socket
  ```

- 设置 TCP Socket

  ```shell
  systemctl edit docker.service
  ```

  ```shell
  [Service]
  ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375
  ```

## 常用命令

- 删除已停止的容器

  ```shell
  docker rm $(docker ps -a | grep Exited | awk '{print $1}')
  ```

- 获取最新镜像版本号

  ```shell
  VERSION=$(docker image ls | grep 'nginx' | awk '{print $2}' | sort -r | head -n 1)
  ```

- 自动清理空间

  ```shell
  docker system prune
  ```

- 查看占用

  ```shell
  docker system df
  ```
