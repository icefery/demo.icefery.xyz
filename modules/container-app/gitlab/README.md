# GitLab

## GitLab

#### Docker 安装 GitLab

```bash
nerdctl create network compose

nerdctl compose up -d

nerdctl exec -it gitlab cat /etc/gitlab/initial_root_password
```

#### Helm 安装 GitLab

```bash
helm repo add gitlab http://charts.gitlab.io/

helm repo update

helm upgrade gitlab gitlab/gitlab --install --namespace gitlab --create-namespace --values gitlab.yaml --version 6.2.2

kubectl get secret -n gitlab gitlab-gitlab-initial-root-password -ojsonpath='{.data.password}' | base64 --decode ; echo
```

## GitLab Runner

#### Shell 执行器

```bash
curl -L --output /usr/local/bin/gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64

chmod +x /usr/local/bin/gitlab-runner

# 直接使用 root 用户运行 runner 但是工作目录依然在原先的用户主目录下
mkdir -p /home/gitlab-runner

gitlab-runner install --user=root --working-directory=/home/gitlab-runner

gitlab-runner start
```

#### SSH 执行器

- 生成 SSH 秘钥

  ```bash
  ssh-keygen -t rsa -q -N "" -f ~/.ssh/gitlab-runner

  cat ~/.ssh/gitlab-runner.pub >> ~/.ssh/authorized_keys
  ```

- 启动容器

  ```bash
  nerdctl compose -f gitlab-runner-ssh.compose.yaml up -d
  ```

- 注册

  ```bash
  gitlab-runner register --url http://gitlab.dev.icefery.xyz/ --registration-token <TOKEN>
  ```

- 解决 `error: ssh: handshake failed: knownhosts: key is unknown`

  ```bash
  echo "    disable_strict_host_key_checking = true" >> /etc/gitlab-runner/config.toml

  gitlab-runner restart
  ```

#### Kubernetes 执行器

```bash
helm repo add gitlab http://charts.gitlab.io/

helm repo update

helm upgrade gitlab-runner gitlab/gitlab-runner --install --namespace gitlab --create-namespace --values gitlab-runner.yaml --version 0.43.1
```

## GitLab Agent

#### Helm 安装 GitLab Agent

```bash
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

## 注意事项

#### GitLab 并不能识别 Ingress Controller 暴露的非 80 和 443 以外的端口
