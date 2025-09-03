# Containerd

## 安装

```shell
REPO="containerd/nerdctl"
TAG=$(curl -fsSL "https://api.github.com/repos/${REPO}/releases/latest" | jq -r ".tag_name")
ASSET="nerdctl-full-${TAG//v/}-linux-$(dpkg --print-architecture).tar.gz"

wget "https://github.com/${REPO}/releases/download/${TAG}/${ASSET}" -O "${ASSET}"

tar Cxzvvf /usr/local "${ASSET}"

systemctl enable --now containerd

systemctl enable --now buildkit

nerdctl completion bash > /etc/bash_completion.d/nerdctl

source /etc/profile

nerdctl network create compose

nerdctl run --privileged --rm tonistiigi/binfmt --install all
```

### 查看默认配置

```shell
containerd config default
```

### 命令补全

```shell
nerdctl completion bash > /etc/bash_completion.d/nerdctl

source /etc/profile
```

### 设置镜像源

> -   https://github.com/containerd/containerd/blob/main/docs/cri/registry.md
> -   https://github.com/containerd/containerd/blob/main/docs/hosts.md

```shell
mkdir -p /etc/containerd/certs.d/docker.io

cat > /etc/containerd/certs.d/docker.io/hosts.toml <<- "EOF"
server = "https://registry-1.docker.io"
[host."https://uwk49ut2.mirror.aliyuncs.com"]
  capabilities = ["pull"]
EOF
```

```shell
mkdir -p /etc/containerd

cat > /etc/containerd/config.toml <<- "EOF"
version = 2

[plugins."io.containerd.grpc.v1.cri".registry]
  config_path = "/etc/containerd/certs.d"
EOF
```

## 设置守护进程代理

```shell
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

> -   https://docs.docker.com/build/buildx/multiplatform-images/
> -   https://docs.docker.com/engine/reference/commandline/manifest/

#### 配置 QEMU 多平台支持

```shell
# docker run --privileged --rm tonistiigi/binfmt --install all

nerdctl run --privileged --rm tonistiigi/binfmt --install all

ls -1 /proc/sys/fs/binfmt_misc/qemu*
```

#### 通过 `docker` 构建

```shell
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

```shell
nerdctl build -t icefery/my-app:0.0.1 --platform linux/arm64,linux/amd64 .

nerdctl image ls

nerdctl login

nerdctl push --all-platforms icefery/my-app:0.0.1
```

## 常见问题

#### `FATA[0000] failed to create shim task: OCI runtime create failed: runc create failed: mountpoint for devices not found: unknown`

-   [docker 启动问题 mountpoint for devices not found](https://blog.csdn.net/weixin_47023868/article/details/116025586)
-   [https://github.com/tianon/cgroupfs-mount](https://github.com/tianon/cgroupfs-mount)

#### `ctr` 拉取 HTTP 镜像

```shell
ctr image pull --plain-http <image>
```
