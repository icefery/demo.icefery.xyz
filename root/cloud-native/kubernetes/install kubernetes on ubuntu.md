## 前置条件

```shell
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
overlay
br_netfilter
EOF

sudo modprobe overlay
sudo modprobe br_netfilter

cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-iptables  = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.ipv4.ip_forward                 = 1
EOF

sudo sysctl --system
```

## 配置容器运行时 Containerd

### 设置守护进程代理

```shell
mkdir -p /etc/systemd/system/containerd.service.d

cat <<- EOF > /etc/systemd/system/containerd.service.d/proxy.conf
[Service]
Environment="HTTP_PROXY=http://192.192.192.10:7890"
Environment="HTTPS_PROXY=http://192.192.192.10:7890"
EOF

systemctl daemon-reload && systemctl restart containerd
```

### 设置 Cgroup

```shell
sed -i 's#SystemdCgroup = false#SystemdCgroup = true#g' /etc/containerd/config.toml

systemctl restart containerd
```

## 安装 `kubelet`、`kubeadm`、`kubectl`

### 说明

-   `kubeadm`：用来初始化集群的指令。
-   `kubelet`：在集群中的每个节点上用来启动 Pod 和容器等。
-   `kubectl`：用来与集群通信的命令行工具。

### 设置代理

```shell
alias set-proxy='export http_proxy=http://192.192.192.10:7890 https_proxy=http://192.192.192.10:7890'
alias unset-proxy='unset http_proxy https_proxy'

set-proxy
```

### 安装

```shell
apt-get update && apt-get install -y apt-transport-https

curl -o /usr/share/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg

cat <<- EOF > /etc/apt/sources.list.d/kubernetes.list
deb [signed-by=/usr/share/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main
EOF

apt-get update && apt-get install -y kubelet kubeadm kubectl

apt-mark hold kubelet kubeadm kubectl
```

### `kubectl` 自动补全

```shell
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null
```

## 安装 `flanneld`

```shell
mkdir -p /opt/bin && wget -O /opt/bin/flanneld https://github.com/flannel-io/flannel/releases/download/v0.17.0/flanneld-amd64
```

## 拉取镜像

```shell
crictl config runtime-endpoint unix:///run/containerd/containerd.sock
```

```shell
kubeadm config images list

kubeadm config images pull

# kubeadm config images list | xargs nerdctl pull --namespace k8s.io
```

## 创建集群

### 取消代理

```shell
unset http_proxy https_proxy
```

### 重置集群

```shell
kubeadm reset
```

### 初始化集群

```shell
kubeadm init --pod-network-cidr=10.244.0.0/16

kubectl apply -f https://raw.githubusercontent.com/flannel-io/flannel/master/Documentation/kube-flannel.yml
```

```shell
cat <<- EOF >> /etc/bash.bashrc
export KUBECONFIG=/etc/kubernetes/admin.conf
EOF
```

### 加入集群

-   创建 Token

    ```shell
    kubeadm token create --print-join-command
    ```

## 设置主节点可调度

```shell
kubectl taint nodes --all node-role.kubernetes.io/control-plane- node-role.kubernetes.io/master-
```

## 安装 `kubernetes-dashboard`

### 创建资源

```shell
wget -O kubernetes-dashboard.yaml https://raw.githubusercontent.com/kubernetes/dashboard/v2.5.1/aio/deploy/recommended.yaml

# 将 ClusterIP 修改为 NodePort

kubectl apply -f kubernetes-dashboard.yaml
```

```shell
kubectl create serviceaccount admin-user --namespace=kubernetes-dashboard

kubectl create clusterrolebinding admin-user --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:admin-user
```

### 获取 Token

```shell
kubectl -n kubernetes-dashboard get secret $(kubectl -n kubernetes-dashboard get sa/admin-user -o jsonpath="{.secrets[0].name}") -o go-template="{{.data.token | base64decode}}"
```

## Helm

### 安装

```shell
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
```

### 自动补全

```shell
helm completion bash > /etc/bash_completion.d/helm
```
