# K3S

## 一、安装

> -   [私有镜像仓库配置](https://docs.k3s.io/zh/installation/private-registry)
> -   [配置 HTTP 代理](https://docs.k3s.io/zh/advanced?_highlight=no_pro#配置-http-代理)

### 2.1 快速安装

#### 2.1.1 启动 Server 节点

```shell
# 可禁用的组件有 coredns | servicelb | traefik | local-storage | metrics-server
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.26.12+k3s1 bash -s - server \
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

### 2.2 高可用嵌入式 etcd 安装

> [高可用嵌入式 etcd](https://docs.k3s.io/datastore/ha-embedded)

#### 2.2.1 启动 Server 节点

> 使用 `--cluster-init` 标志引导集群。

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.26.12+k3s1 bash -s - server \
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

#### 2.2.2 加入其它 Server 节点

> 配置标识在所有 Server 节点必须是相同的。

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.26.12+k3s1 bash -s - server \
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

### 2.3 高可用外部数据库安装

> [高可用外部数据库](https://docs.k3s.io/zh/datastore/ha)

#### 2.3.1 启动 Server 节点

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.26.12+k3s1 bash -s - server \
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
    --datastore-endpoint "mysql://username:password@tcp(hostname:3306)/database-name"
```

```shell
cat /data/k3s/var/lib/rancher/k3s/server/token
```

#### 2.3.2 其它 Server 节点

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.26.12+k3s1 bash -s - server \
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
    --datastore-endpoint "mysql://username:password@tcp(hostname:3306)/database-name" \
    --token <TOKEN>
```

### 2.4 加入 Agent 节点

```shell
curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.26.12+k3s1 bash -s - agent \
    --data-dir /data/k3s/var/lib/rancher/k3s \
    --server https://<HOST>:6443 \
    --token <TOKEN>
```

## 收藏

#### 使用 `nerdctl` 访问 Containerd

```shell
nerdctl --address /var/run/k3s/containerd/containerd.sock ps -a
```

#### bitnami 镜像卷映射权限不足

```shell
docker compose up -d

chown -R 1001:1001 /data/compose

docker compose restart
```
