# Traefik Ingress Controller

## 安装

```bash
helm repo add traefik https://helm.traefik.io/traefik

helm repo update

helm upgrade traefik traefik/traefik \
  --install \
  --create-namespace \
  --namespace traefik \
  --values values.yaml \
  --version 10.24.0
```

## 常见问题

- [Unable to bind to port 53 #516](https://github.com/traefik/traefik-helm-chart/issues/516)

- [Default values on securityContext will render the container useless #163](https://github.com/traefik/traefik-helm-chart/issues/163)