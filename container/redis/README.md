# Redis

## Install

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade redis bitnami/redis \
    --install \
    --create-namespace \
    --namespace redis \
    --version 16.12.2 \
    --values values.yaml
```