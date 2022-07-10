# Harbor

## Install

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade harbor bitnami/harbor \
    --install \
    --create-namespace \
    --namespace harbor \
    --version 13.2.7 \
    --values values.yaml
```
