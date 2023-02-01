# External-DNS

## 安装

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade external-dns bitnami/external-dns --install --namespace kube-system --values values.yaml --version 6.7.4
```
