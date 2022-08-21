# GitLab

## Docker 安装 GitLab

```bash
nerdctl create network compose

nerdctl compose up -d

nerdctl exec -it gitlab cat /etc/gitlab/initial_root_password
```

## Helm 安装 GitLab

```bash
helm repo add gitlab http://charts.gitlab.io/

helm repo update

helm upgrade gitlab gitlab/gitlab --install --namespace gitlab --create-namespace --values gitlab.yaml --version 6.2.2

kubectl get secret -n gitlab gitlab-gitlab-initial-root-password -ojsonpath='{.data.password}' | base64 --decode ; echo
```

## Helm 安装 GitLab Runner

```bash
helm repo add gitlab http://charts.gitlab.io/

helm repo update

helm upgrade gitlab-runner gitlab/gitlab-runner --install --namespace gitlab --create-namespace --values gitlab-runner.yaml --version 0.43.1
```

## Helm 安装 GitLab Agent

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
