# Harbor

## 安装

```shell
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade harbor bitnami/harbor --install --namespace harbor --create-namespace --values values.yaml --version 15.1.0
```

## 镜像仓库

#### 配置 Containerd 镜像仓库

> -   https://github.com/containerd/containerd/blob/main/docs/cri/registry.md
> -   https://github.com/containerd/containerd/blob/main/docs/hosts.md

-   `/etc/containerd/certs.d/docker.io/hosts.toml`

    ```toml
    server = "https://registry-1.docker.io"
    [host."https://uwk49ut2.mirror.aliyuncs.com"]
      capabilities = ["pull"]
    ```

-   `/etc/containerd/certs.d/core.harbor.dev.icefery.xyz/hosts.toml`

    ```toml
    server = "http://core.harbor.dev.icefery.xyz"
    [host."http://core.harbor.dev.icefery.xyz"]
      skip_verify = true
    ```

-   `/etc/containerd/config.toml`

    > Although we have deprecated the old CRI config pattern for specifying registry.mirrors and registry.configs you can
    > still specify your credentials
    > via [CRI config](https://github.com/containerd/containerd/blob/main/docs/cri/registry.md#configure-registry-credentials)
    > .

    ```toml
    version = 2

    [plugins."io.containerd.grpc.v1.cri".registry]
      config_path = "/etc/containerd/certs.d"
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

#### 配置 K3S 内置 Containerd 镜像仓库

> https://docs.rancher.cn/docs/k3s/installation/private-registry/_index

-   `/etc/rancher/k3s/registries.yaml`
    ```yaml
    mirrors:
      docker.io:
        endpoint:
          - 'https://uwk49ut2.mirror.aliyuncs.com'
      'core.harbor.dev.icefery.xyz':
        endpoint:
          - 'http://core.harbor.dev.icefery.xyz'
    configs:
      'core.harbor.dev.icefery.xyz':
        tls:
          insecure_skip_verify: true
        auth:
          username: admin
          password: admin
    ```

#### 推送镜像

> nerdctl 并不走 CRI 的 `tls.insecure_skip_verify`，需要添加 `--insecure-registry` 选项。

```shell
docker login http://core.harbor.dev.icefery.xyz --username=admin --password=admin

# nerdctl login http://core.harbor.dev.icefery.xyz --username=admin --password=admin --insecure-registry
```

```shell
docker push core.harbor.dev.icefery.xyz/library/my-app:0.0.1

# nerdctl push core.harbor.dev.icefery.xyz/library/my-app:0.0.1 --insecure-registry
```

```shell
docker pull core.harbor.dev.icefery.xyz/library/my-app:0.0.1

# nerdctl pull core.harbor.dev.icefery.xyz/library/my-app:0.0.1 --insecure-registry
```

## Chart 仓库

#### 拉取

```shell
# 添加 Harbor 作为统一的单一索引入口点
# helm repo add chartmuseum-global http://core.harbor.dev.icefery.xyz/chartrepo --username=admin --password=admin

# 将 Harbor 项目添加为单独的索引入口点
helm repo add chartmuseum-library http://core.harbor.dev.icefery.xyz/chartrepo/library --username=admin --password=admin
```

```shell
helm search repo my-app
```

#### 推送

```shell
helm plugin install https://github.com/chartmuseum/helm-push
```

```shell
# helm cm-push chart/ http://core.harbor.dev.icefery.xyz/chartrepo/library --username=admin --password=admin

helm cm-push chart/ chartmuseum-library
```

> 直接推送到已添加的仓库中不需要再验证用户和密码，但是使用 Harbor 作为统一的单一索引入口点时会默认推送到 `library` 项目。

## 常见问题

#### Docker 无法 `push` 到 HTTP 镜像仓库

> [docker pull push invalid character 'p' after top-level value: "404 page not found\n" #12248](https://github.com/goharbor/harbor/issues/12248)
