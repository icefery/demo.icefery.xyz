# External-DNS

## 安装

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade external-dns bitnami/external-dns --install --namespace external-dns --create-namespace --values values.yaml --version 6.7.4
```

## TODO

- 通过密码连接 etcd