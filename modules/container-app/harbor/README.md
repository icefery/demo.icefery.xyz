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
