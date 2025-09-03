# Docker

## 快速开始

> [Install Docker Engine on Ubuntu](https://docs.docker.com/engine/install/ubuntu/#install-using-the-convenience-script)

## 收藏

### [关于 Dockerfile 中 echo 的用法](https://www.jianshu.com/p/7c7c6c2c6f6b#comments)

### [docker 挂载数据卷](https://www.cnblogs.com/kerwincui/p/12544603.html)

### [`Warning: Stopping docker.service, but it can still be activated by: docker.socket`](https://blog.csdn.net/weixin_43885975/article/details/117809901)

### 远程访问

> https://docs.docker.com/config/daemon/remote-access/#configuring-remote-access-with-systemd-unit-file

#### 方式一

-   `/etc/systemd/system/multi-user.target.wants/docker.service`

    ```toml
    ExecStart=/usr/bin/dockerd -H fd:// -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375
    ```

#### 方式二

-   `/etc/systemd/system/multi-user.target.wants/docker.service`

    ```toml
    ExecStart=/usr/bin/dockerd
    ```

-   `/etc/docker/daemon.json`

    ```json
    {
      "data-root": "/opt/data/docker",
      "registry-mirrors": ["https://uwk49ut2.mirror.aliyuncs.com"],
      "hosts": ["unix:///var/run/docker.sock", "tcp://0.0.0.0:2375"]
    }
    ```

#### 注意项

访问指令只能选择一处进行配置，否则会报错：

```text
unable to configure the Docker daemon with file /etc/docker/daemon.json: the following directives are specified both as a flag and in the configuration file: hosts: (from flag: [fd:// unix:///var/run/docker.sock], from file: [tcp://0.0.0.0:2375])
```

#### 测试

```shell
sudo daemon-reload

sudo systemctl restart docker

sudo netstat -lntp | grep dockerd
```

## 常用命令

-   删除已停止的容器

    ```shell
    docker rm $(docker ps -a | grep Exited | awk '{print $1}')
    ```

-   获取最新镜像版本号

    ```shell
    VERSION=$(docker image ls | grep 'nginx' | awk '{print $2}' | sort -r | head -n 1)
    ```

-   自动清理空间

    ```shell
    docker system prune
    ```

-   查看占用

    ```shell
    docker system df
    ```
