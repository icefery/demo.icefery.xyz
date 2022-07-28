# Harbor

## 安装

```bash
helm repo add harbor https://helm.goharbor.io

helm repo update

helm upgrade harbor harbor/harbor \
  --install \
  --create-namespace \
  --namespace harbor \
  --values values.yaml \
  --version 1.9.3
```

## 镜像仓库

### Containerd 配置镜像仓库

```bash
version = 2

[plugins."io.containerd.grpc.v1.cri".registry.mirrors]
[plugins."io.containerd.grpc.v1.cri".registry.mirrors."docker.io"]
  endpoint = ["https://uwk49ut2.mirror.aliyuncs.com"]
[plugins."io.containerd.grpc.v1.cri".registry.mirrors."core.harbor.dev.icefery.xyz"]
  endpoint = ["http://core.harbor.dev.icefery.xyz"]
[plugins."io.containerd.grpc.v1.cri".registry.configs."core.harbor.dev.icefery.xyz".tls]
  insecure_skip_verify = true
[plugins."io.containerd.grpc.v1.cri".registry.configs."core.harbor.dev.icefery.xyz".auth]
  username = "admin"
  password = "admin"
```
