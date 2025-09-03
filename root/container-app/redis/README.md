# Redis

## Helm 安装 Redis

### Helm

```shell
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade redis bitnami/redis --install --namespace redis --create-namespace --values values.yaml --version 17.0.11
```
