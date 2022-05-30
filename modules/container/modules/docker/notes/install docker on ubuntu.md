## 卸载

```bash
apt-get remove docker docker-engine docker.io containerd runc
```

## 设置存储库

```bash
apt-get update && apt-get install ca-certificates curl gnupg lsb-release

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

```bash
sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/apt/sources.list.d/docker.list
```

## 安装

```bash
apt-get update && apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

```bash
apt-get remove docker docker-engine docker.io containerd runc
```

## 设置镜像源

```bash
echo '{ "registry-mirrors": ["https://uwk49ut2.mirror.aliyuncs.com"] }' > /etc/docker/daemon.json

systemctl daemon-reload

systemctl restart docker
```

## 设置 TCP Socket

```bash
systemctl edit docker.service
```

```bash
[Service]
ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:2375
```

## 常用命令

- 删除已停止的容器

  ```bash
  docker rm $(docker ps -a | grep Exited | awk '{print $1}')
  ```

- 获取最新镜像版本号

  ```bash
  VERSION=$(docker image ls | grep 'nginx' | awk '{print $2}' | sort -r | head -n 1)
  ```

- 自动清理空间
  ```bash
  docker system prune
  ```
- 查看占用
  ```bash
  docker system df
  ```
