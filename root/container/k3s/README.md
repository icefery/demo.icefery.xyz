# K3S

## 安装

#### 卸载

```bash
/usr/local/bin/k3s-uninstall.sh

/usr/local/bin/k3s-agent-uninstall.sh
```

#### 配置 Containerd 镜像源

```bash
mkdir -p /etc/rancher/k3s

cat > /etc/rancher/k3s/registries.yaml <<- "EOF"
mirrors:
  docker.io:
    endpoint:
      - "https://uwk49ut2.mirror.aliyuncs.com"
EOF
```

#### 安装

```bash
# 可禁用的组件有 coredns | servicelb | traefik | local-storage | metrics-server
curl -sfL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn sh -s - \
  --service-node-port-range 1-65535 \
  --disable servicelb \
  --disable traefik \
  --disable metrics-server
```

#### 配置 `KUBECONFIG` 环境变量

```bash
echo "export KUBECONFIG=/etc/rancher/k3s/k3s.yaml" >> /etc/custom.sh

source /etc/profile
```

#### 配置 `kubectl` 命令补全

```bash
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

source /etc/profile
```

## 常用命令

#### 使用 `nerdctl` 访问 Containerd

```bash
nerdctl --address /var/run/k3s/containerd/containerd.sock ps -a
```
