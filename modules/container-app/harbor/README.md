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

### 配置 Containerd 镜像仓库

> https://github.com/containerd/containerd/blob/main/docs/cri/registry.md

- `/etc/containerd/config.toml`

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

### 配置 K3S 内置 Containerd 镜像仓库

> https://docs.rancher.cn/docs/k3s/installation/private-registry/_index

- `/etc/rancher/k3s/registries.yaml`
  ```yaml
  mirrors:
    docker.io:
      endpoint:
        - "https://uwk49ut2.mirror.aliyuncs.com"
    "core.harbor.dev.icefery.xyz":
      endpoint:
        - "http://core.harbor.dev.icefery.xyz"
  configs:
    "core.harbor.dev.icefery.xyz":
      tls:
        insecure_skip_verify: true
      auth:
        username: admin
        password: admin
  ```

### 推送镜像

> nerdctl 并不走 CRI 的 `tls.insecure_skip_verify`，需要添加 `--insecure-registry` 选项。

```bash
docker login http://core.harbor.dev.icefery.xyz --username=admin --password=admin

# nerdctl login http://core.harbor.dev.icefery.xyz --username=admin --password=admin --insecure-registry
```

```bash
docker push core.harbor.dev.icefery.xyz/icefery/my-app:0.0.1

# nerdctl push core.harbor.dev.icefery.xyz/icefery/my-app:0.0.1 --insecure-registry
```

```bash
docker pull core.harbor.dev.icefery.xyz/icefery/my-app:0.0.1

# nerdctl pull core.harbor.dev.icefery.xyz/icefery/my-app:0.0.1 --insecure-registry
```

## 配置 Helm Chart 仓库

### 拉取

```bash
# 添加 Harbor 作为统一的单一索引入口点
helm repo add --username=admin --password=admin chartmuseum http://core.harbor.dev.icefery.xyz/chartrepo

# 将 Harbor 项目添加为单独的索引入口点
# helm repo add --username=admin --password=admin chartmuseum-icefery http://core.harbor.dev.icefery.xyz/chartrepo/icefery
```

```bash
helm search repo my-app
```

### 推送

```bash
helm plugin install https://github.com/chartmuseum/helm-push
```

```bash
helm cm-push --username=admin --password=admin my-app/chart/ http://core.harbor.dev.icefery.xyz/chartrepo/icefery

# helm cm-push my-app/chart/ chartmuseum-icefery
```

> 直接推送到已添加的仓库中不需要再验证用户和密码，但是使用 Harbor 作为统一的单一索引入口点时会默认推送到 `library` 项目。
