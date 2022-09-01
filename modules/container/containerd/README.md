# Containerd

## 安装

```bash
wget https://github.com/containerd/nerdctl/releases/download/v0.22.0/nerdctl-full-0.22.0-linux-amd64.tar.gz

tar Cxzvvf /usr/local nerdctl-full-0.22.0-linux-amd64.tar.gz

systemctl enable --now containerd

systemctl enable --now buildkit

nerdctl run --privileged --rm tonistiigi/binfmt --install all
```

### 查看默认配置

```bash
containerd config default
```

### 命令补全

```bash
nerdctl completion bash > /etc/bash_completion.d/nerdctl

source /etc/profile
```

### 设置镜像源

> - https://github.com/containerd/containerd/blob/main/docs/cri/registry.md
> - https://github.com/containerd/containerd/blob/main/docs/hosts.md

```bash
mkdir -p /etc/containerd/certs.d/docker.io

cat > /etc/containerd/certs.d/docker.io/hosts.toml <<- "EOF"
server = "https://registry-1.docker.io"
[host."https://uwk49ut2.mirror.aliyuncs.com"]
  capabilities = ["pull"]
EOF
```

```bash
mkdir -p /etc/containerd

cat > /etc/containerd/config.toml <<- "EOF"
version = 2

[plugins."io.containerd.grpc.v1.cri".registry]
  config_path = "/etc/containerd/certs.d"
EOF
```

## 设置守护进程代理

```bash
mkdir -p /etc/systemd/system/containerd.service.d

cat > /etc/systemd/system/containerd.service.d/proxy.conf <<- "EOF"
[Service]
Environment="HTTP_PROXY=http://192.192.192.10:7890"
Environment="HTTPS_PROXY=http://192.192.192.10:7890"
EOF

systemctl daemon-reload

systemctl restart containerd
```

## 构建多平台镜像

> - https://docs.docker.com/build/buildx/multiplatform-images/
> - https://docs.docker.com/engine/reference/commandline/manifest/

#### 配置 QEMU 多平台支持

```bash
# docker run --privileged --rm tonistiigi/binfmt --install all

nerdctl run --privileged --rm tonistiigi/binfmt --install all

ls -1 /proc/sys/fs/binfmt_misc/qemu*
```

#### 通过 `docker` 构建

```bash
# 创建 Builder
docker buildx create --use
docker buildx ls

# 构建多个平台镜像并推送到 DockerHub
docker login
docker buildx build --tag icefery/my-app:0.0.1 --platform linux/amd64,linux/arm64 --push .

# 构建单个平台并导出到本地
docker buildx build --tag icefery/my-app:0.0.1 --platform linux/arm64 --load .
```

> 导出到本地只能构建一个镜像，本地不支持同时导出 manifest lists。

#### 通过 `nerdctl` 构建

```bash
nerdctl build -t icefery/my-app:0.0.1 --platform linux/arm64,linux/amd64 .

nerdctl image ls

nerdctl login

nerdctl push --all-platforms icefery/my-app:0.0.1
```
