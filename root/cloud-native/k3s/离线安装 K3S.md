## 一、前言

简要记录一下离线环境下 K3S 的搭建，版本为 `v1.23.17+k3s1`，使用外部数据库 MySQL 作元数据存储，禁用默认组件（`coredns`、`servicelb`、`traefik`、`local-storage`、`metrics-server`）并使用 Helm 单独安装（`coredns`、`metrics-server`、`traefik`、`longhorn`）。

需要一台联网主机（虚拟机），和多台未联网主机（服务器）。

## 二、联网虚拟机

### 2.1 快速引导一个单节点集群

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
    --disable metrics-server
```

### 2.2 快速安装 Longhorn 的依赖

> [Installation Requirements](https://longhorn.io/docs/1.5.1/deploy/install/#installation-requirements)

```shell
# yum
yum install iscsi-initiator-utils nfs-utils

# ubuntu
apt install open-iscsi nfs-common

# 启动
systemctl enable iscsid --now
```

### 2.3 快速安装应用（通过 Helm Controller）

```yaml
### coredns
---
apiVersion: helm.cattle.io/v1
kind: HelmChart
metadata:
  name: coredns
  namespace: kube-system
  labels:
    app: coredns
spec:
  repo: https://coredns.github.io/helm
  chart: coredns
  version: 1.29.0
  targetNamespace: kube-system
  bootstrap: true
  valuesContent: |-
    fullnameOverride: coredns
    serviceType: ClusterIP
    prometheus:
      service:
        enabled: true
    service:
      name: coredns
      clusterIP: 10.16.0.10
    servers:
      - zones:
          - zone: .
        port: 53
        plugins:
          - name: errors
          - name: health
            configBlock: |-
              lameduck 5s
          - name: ready
          - name: kubernetes
            parameters: cluster.local in-addr.arpa ip6.arpa
            configBlock: |-
              pods insecure
              fallthrough in-addr.arpa ip6.arpa
              ttl 30
          - name: prometheus
            parameters: 0.0.0.0:9153
          - name: forward
            parameters: . /etc/resolv.conf
          - name: cache
            parameters: 30
          - name: loop
          - name: reload
          - name: loadbalance


###  metrics-server
---
apiVersion: helm.cattle.io/v1
kind: HelmChart
metadata:
  name: metrics-server
  namespace: kube-system
  labels:
    app: metrics-server
spec:
  repo: https://charts.bitnami.com/bitnami
  chart: metrics-server
  version: 6.8.0
  targetNamespace: kube-system
  bootstrap: true
  valuesContent: |
    apiService:
      create: true
    extraArgs:
      - --kubelet-insecure-tls
      - --kubelet-use-node-status-port
      - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
      - --metric-resolution=15s


### traefik
---
apiVersion: v1
kind: Namespace
metadata:
  name: traefik-system

---
apiVersion: helm.cattle.io/v1
kind: HelmChart
metadata:
  name: traefik
  namespace: traefik-system
  labels:
    app: traefik
spec:
  repo: https://traefik.github.io/charts
  chart: traefik
  version: 26.0.0
  targetNamespace: traefik-system
  bootstrap: true
  valuesContent: |-
    deployment:
      kind: Deployment
    ingressClass:
      enabled: true
      isDefaultClass: true
    providers:
      kubernetesCRD:
        enabled: true
        allowCrossNamespace: true
        allowExternalNameServices: true
        allowEmptyServices: true
      kubernetesIngress:
        enabled: true
        allowExternalNameServices: true
        allowEmptyServices: true
        publishedService:
          enabled: true
    ports:
      traefik:
        port: 9000
        protocol: TCP
        expose: false
        exposedPort: 9000
      metrics:
        port: 9100
        protocol: TCP
        expose: false
        exposedPort: 9100
      web:
        port: 80
        protocol: TCP
        expose: true
        exposedPort: 80
        nodePort: 30080
      websecure:
        port: 443
        protocol: TCP
        expose: true
        exposedPort: 443
        ndoePort: 30443
        tls:
          enabled: true
    service:
      type: NodePort
    securityContext:
      capabilities:
        drop: []
        add: [NET_BIND_SERVICE]
      readOnlyRootFilesystem: false
    podSecurityContext:
      runAsGroup: 0
      runAsNonRoot: false
      runAsUser: 0


### longhorn
---
apiVersion: v1
kind: Namespace
metadata:
  name: longhorn-system

---
apiVersion: helm.cattle.io/v1
kind: HelmChart
metadata:
  name: longhorn
  namespace: longhorn-system
  labels:
    app: longhorn
spec:
  repo: https://charts.longhorn.io
  chart: longhorn
  version: 1.5.3
  targetNamespace: longhorn-system
  bootstrap: true
  valuesContent: |-
    persistence:
      defaultClassReplicaCount: 1
    csi:
      attacherReplicaCount: 1
      provisionerReplicaCount: 1
      resizerReplicaCount: 1
      snapshotterReplicaCount: 1
    defaultSettings:
      defaultDataPath: /data/longhorn
      defaultReplicaCount: 1
      deletingConfirmationFlag: true
    longhornUI:
      replicas: 1
    ingress:
      enabled: true
      host: longhorn.example.org
```

```shell
kubectl apply -f charts.yaml
```

## 三、资源准备

### 3.1 下载 Longhorn 依赖

1. 查看服务器 glibc 版本

    ```shell
    ldd --version
    ```

    | os     | os version | glibc version |
    | :----- | :--------- | :------------ |
    | centos | 7.9        | 2.17          |
    | centos | 8.4        | 2.28          |
    | ubuntu | 18.04      | 2.27          |
    | ubuntu | 20.04      | 2.31          |
    | ubuntu | 22.04      | 2.35          |

2. 创建对应 glibc 版本的容器

    ```shell
    # centos 7
    kubectl run centos --image=centos:7.9.2009 --command -- /bin/sleep infinity
    kubectl exec -it pod/centos -- /bin/bash

    # ubuntu 22
    kubectl run ubuntu --image=ubuntu:22.04 --command -- /bin/sleep infinity
    kubectl exec -it pod/ubuntu -- /bin/bash
    ```

3. 下载依赖

    ```shell
    # yum
    yum install iscsi-initiator-utils nfs-utils --downloadonly --downloaddir=rpm -y
    tar -czvf ./rpm.tar.gz ./rpm

    # apt
    apt update && apt install open-iscsi nfs-common --download-only -y && mkdir -p deb && cp /var/cache/apt/archives/*.deb deb
    tar -czvf ./deb.tar.gz ./deb
    ```

4. 复制出依赖

    ```shell
    # yum
    kubectl cp centos:/rpm.tar.gz ./rpm.tar.gz

    # apt
    kubectl cp ubuntu:/deb.tar.gz ./deb.tar.gz
    ```

### 3.2 下载 K3S 资源

> 参考文档：[离线安装](https://docs.k3s.io/zh/installation/airgap)

```shell
wget https://github.com/k3s-io/k3s/releases/download/v1.23.17+k3s1/k3s-airgap-images-amd64.tar.gz
wget https://github.com/k3s-io/k3s/releases/download/v1.23.17+k3s1/k3s
wget https://get.helm.sh/helm-v3.12.2-linux-amd64.tar.gz
wget https://get.k3s.io -O install.sh
```

### 3.3 下载 HelmChart 和导出镜像

```shell
# 下载 helm chart 包
helm repo add coredns  https://coredns.github.io/helm     && helm pull coredns/coredns        --version 1.29.0
helm repo add bitnami  https://charts.bitnami.com/bitnami && helm pull bitnami/metrics-server --version 6.8.0
helm repo add traefik  https://traefik.github.io/charts   && helm pull traefik/traefik        --version 26.0.0
helm repo add longhorn https://charts.longhorn.io         && helm pull longhorn/longhorn      --version 1.5.3

# 导出镜像
k3s ctr image ls -q | grep -v 'sha256' | sort -u | xargs k3s ctr image export image.tar
```

## 四、未联网服务器

### 4.1 准备 K3S 资源

```shell
# 准备 k3s 镜像
mkdir -p /data/k3s/var/lib/rancher/k3s/agent/images
cp ./k3s-airgap-images-amd64.tar.gz /data/k3s/var/lib/rancher/k3s/agent/images

# 准备 k3s 二进制可执行文件
install ./k3s /usr/local/bin

# 准备 helm 二进制可执行文件
tar -zxvf ./helm-v3.12.2-linux-amd64.tar.gz
install ./linux-amd64/helm /usr/local/bin

# 准备 k3s 安装脚本
chmod +x ./install.sh
```

### 4.2 启动 Server 节点

```shell
# 引导 Server
INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 ./install.sh server \
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
    --datastore-endpoint "mysql://<USERNAME>:<PASSWORD>@tcp(<HOST>:<PORT>)/<DATABASE>"

# 查看 Token
cat /data/k3s/var/lib/rancher/k3s/server/token
```

### 4.3 加入其它 Server 节点

> 配置标识在所有 Server 节点必须是相同的。

```shell
INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 ./install.sh server \
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
    --datastore-endpoint "mysql://<USERNAME>:<PASSWORD>@tcp(<HOST>:<PORT>)/<DATABASE>" \
    --token <TOKEN>
```

### 4.4 加入 Agent 节点

```shell
INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 ./install.sh agent \
    --data-dir /data/k3s/var/lib/rancher/k3s \
    --server "https://<HOST>:6443" \
    --token <TOKEN>
```

### 4.5 安装 Longhorn 依赖

```shell
# yum
tar -zxvf rpm.tar.gz
rpm -ivh ./rpm/*.rpm

# apt
tar -zxvf deb.tar.gz
apt install ./deb/*.deb
```

### 4.4 导入镜像和安装应用

```shell
# 导出镜像
k3s ctr image import ./image.tar

# coredns
helm install coredns coredns-1.29.0.tgz --namespace kube-system --values <VALUES_YAML_FILE>
```
