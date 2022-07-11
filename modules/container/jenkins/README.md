# Jenkins

## Install

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade jenkins bitnami/jenkins \
    --install \
    --create-namespace \
    --namespace jenkins \
    --version 10.2.4 \
    --values values.yaml
```