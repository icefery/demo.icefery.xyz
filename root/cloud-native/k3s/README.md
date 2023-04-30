# K3S

## 快速开始

#### 配置 Containerd 镜像源

> [私有镜像仓库配置](https://docs.k3s.io/zh/installation/private-registry)

#### 高可用安装

```shell
export INSTALL_K3S_MIRROR=cn
export INSTALL_K3S_VERSION=v1.24.13+k3s1

# 第一个节点
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | K3S_TOKEN=SECRET sh -s - server \
    --cluster-init \
    --disable servicelb \
    --disable traefik

# 第二第三个节点
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | K3S_TOKEN=SECRET sh -s - server \
    --server https://192.168.8.101:6443 \
    --disable servicelb \
    --disable traefik
```

## 收藏

#### 使用 `nerdctl` 访问 Containerd

```shell
nerdctl --address /var/run/k3s/containerd/containerd.sock ps -a
```
