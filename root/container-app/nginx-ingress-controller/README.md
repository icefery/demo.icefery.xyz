# Nginx Ingress Controller

## 安装

```shell
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade nginx-ingress-controller bitnami/nginx-ingress-controller --install --namespace kube-system --values values.yaml --version 9.2.28
```
