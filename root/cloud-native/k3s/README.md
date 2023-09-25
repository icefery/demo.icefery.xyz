# K3S

## 快速开始

#### 配置 Containerd 镜像源

> [私有镜像仓库配置](https://docs.k3s.io/zh/installation/private-registry)

#### Server 节点

```shell
# 可禁用的组件有 coredns | servicelb | traefik | local-storage | metrics-server
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 bash -s - server \
    --data-dir /data/k3s/var/lib/rancher/k3s \
    --cluster-cidr 10.8.0.0/16 \
    --service-cidr 10.16.0.0/16 \
    --cluster-dns 10.16.0.10 \
    --service-node-port-range 1-65535 \
    --kube-proxy-arg proxy-mode=ipvs \
    --disable coredns \
    --disable servicelb \
    --disable traefik \
    --disable local-storage \
    --disable metrics-server
```

```shell
cat /data/k3s/var/lib/rancher/k3s/server/token
```

#### Agent 节点

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 bash -s - agent \
    --data-dir /data/k3s/var/lib/rancher/k3s \
    --server https://<HOST>:6443 \
    --token <TOKEN>
```

## 高可用嵌入式 etcd 安装

> [高可用嵌入式 etcd](https://docs.k3s.io/datastore/ha-embedded)

#### 第一个 Server 节点

> 使用 `--cluster-init` 标志引导集群。

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 bash -s - server \
    --data-dir /data/k3s/var/lib/rancher/k3s \
    --cluster-cidr 10.8.0.0/16 \
    --service-cidr 10.16.0.0/16 \
    --cluster-dns 10.16.0.10 \
    --service-node-port-range 1-65535 \
    --kube-proxy-arg proxy-mode=ipvs \
    --disable coredns \
    --disable servicelb \
    --disable traefik \
    --disable local-storage \
    --disable metrics-server \
    --cluster-init
```

```shell
cat /data/k3s/var/lib/rancher/k3s/server/token
```

#### 其它 Server 节点

> 配置标识在所有 Server 节点必须是相同的。

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 bash -s - server \
    --data-dir /data/k3s/var/lib/rancher/k3s \
    --cluster-cidr 10.8.0.0/16 \
    --service-cidr 10.16.0.0/16 \
    --cluster-dns 10.16.0.10 \
    --service-node-port-range 1-65535 \
    --kube-proxy-arg proxy-mode=ipvs \
    --disable coredns \
    --disable servicelb \
    --disable traefik \
    --disable local-storage \
    --disable metrics-server \
    --server https://<HOST>:6443 \
    --token <TOKEN>
```

## 高可用外部数据库安装

> [高可用外部数据库](https://docs.k3s.io/zh/datastore/ha)

```shell
# --cluster-init
# --server
--datastore-endpoint="mysql://username:password@tcp(hostname:3306)/database-name"
```

## 收藏

#### 使用 `nerdctl` 访问 Containerd

```shell
nerdctl --address /var/run/k3s/containerd/containerd.sock ps -a
```
