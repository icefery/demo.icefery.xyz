# Nginx Ingress Controller

## Install

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade nginx-ingress-controller bitnami/nginx-ingress-controller \
    --install \
    --create-namespace \
    --namespace nginx-ingress-controller \
    --version 9.2.11 \
    --values values.yaml
```