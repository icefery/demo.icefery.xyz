# Traefik Ingress Controller

## Install

### Helm

```bash
helm repo add traefik https://helm.traefik.io/traefik

helm upgrade traefik traefik/traefik \
    --install \
    --create-namespace \
    --namespace traefik \
    --version 10.21.1 \
    --values values.yaml 
```