# MongoDB

## Helm 安装 MongoDB

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade mongodb bitnami/mongodb --install --namespace mongodb --create-namespace --values values.yaml --version 13.0.2
```
