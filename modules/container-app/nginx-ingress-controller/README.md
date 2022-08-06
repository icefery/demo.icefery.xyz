# Nginx Ingress Controller

## 安装

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade nginx-ingress-controller bitnami/nginx-ingress-controller --install --namespace nginx-ingress-controller --create-namespace --values values.yaml --version 9.2.27
```
