# 极狐 GitLab

## 安装

```bash
helm repo add gitlab-jh https://charts.gitlab.cn

helm repo update

helm upgrade gitlab gitlab-jh/gitlab \
  --install \
  --create-namespace \
  --namespace gitlab \
  --values values.yaml \
  --version 6.2.1
```

### 查看密码

```bash
kubectl get secret -n gitlab gitlab-gitlab-initial-root-password -ojsonpath='{.data.password}' | base64 --decode ; echo
```
