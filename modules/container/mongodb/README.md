# MongoDB

## Install

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade mongodb bitnami/mongodb \
    --install \
    --create-namespace \
    --namespace mongodb \
    --version 12.1.20 \
    --values values.yaml
```