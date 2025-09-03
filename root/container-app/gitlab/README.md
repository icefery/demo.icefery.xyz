# GitLab

## GitLab

#### Docker 安装 GitLab

```shell
nerdctl create network compose

nerdctl compose up -d

nerdctl exec -it gitlab cat /etc/gitlab/initial_root_password
```

#### Helm 安装 GitLab

```shell
# helm repo add gitlab http://charts.gitlab.io/
helm repo add gitlab-jh https://charts.gitlab.cn

helm repo update

# helm upgrade gitlab gitlab/gitlab --install --namespace gitlab --create-namespace --values gitlab.yaml --version 6.3.2
helm upgrade gitlab gitlab-jh/gitlab --install --namespace gitlab --create-namespace --values gitlab.yaml --version 6.3.2

kubectl get secret -n gitlab gitlab-gitlab-initial-root-password -ojsonpath='{.data.password}' | base64 --decode ; echo
```

## GitLab CI

> [指定自定义 CI/CD 配置文件](https://docs.gitlab.cn/jh/ci/pipelines/settings.html#指定自定义-cicd-配置文件)

## GitLab Runner

#### Shell 执行器

```shell
curl -L --output /usr/local/bin/gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64

chmod +x /usr/local/bin/gitlab-runner

# 直接使用 root 用户运行 runner 但是工作目录依然在原先的用户主目录下
mkdir -p /home/gitlab-runner

gitlab-runner install --user=root --working-directory=/home/gitlab-runner

gitlab-runner start
```

#### SSH 执行器

-   生成 SSH 秘钥

    ```shell
    ssh-keygen -t rsa -q -N "" -f ~/.ssh/gitlab-runner

    cat ~/.ssh/gitlab-runner.pub >> ~/.ssh/authorized_keys
    ```

-   启动容器

    ```shell
    nerdctl compose -f gitlab-runner-ssh.compose.yaml up -d
    ```

-   注册

    ```shell
    gitlab-runner register --url http://gitlab.dev.icefery.xyz/ --registration-token <TOKEN>
    ```

-   解决 `error: ssh: handshake failed: knownhosts: key is unknown`

    ```shell
    echo "    disable_strict_host_key_checking = true" >> /etc/gitlab-runner/config.toml

    gitlab-runner restart
    ```

#### Kubernetes 执行器

```shell
helm repo add gitlab http://charts.gitlab.io/

helm repo update

helm upgrade gitlab-runner gitlab/gitlab-runner --install --namespace gitlab --create-namespace --values gitlab-runner.yaml --version 0.43.1
```

## GitLab Agent

#### Helm 安装 GitLab Agent

```shell
helm repo add gitlab https://charts.gitlab.io

helm repo update

helm upgrade gitlab-agent gitlab/gitlab-agent \
  --install \
  --namespace gitlab \
  --create-namespace \
  --set image.tag=v15.2.0 \
  --set config.token=<TOKEN> \
  --set config.kasAddress=ws://kas.dev.icefery.xyz
```

## GitLab MinIO

#### 查看 Access Key 和 Secrey Key

```shell
kubectl get -n gitlab secrets gitlab-minio-secret -ojsonpath='{.data.accesskey}' | base64 --decode

kubectl get -n gitlab secrets gitlab-minio-secret -ojsonpath='{.data.secretkey}' | base64 --decode
```

## 注意事项

#### GitLab 并不能识别 Ingress Controller 暴露的非 80 和 443 以外的端口
