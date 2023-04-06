## Helm 安装 Longhorn

```shell
helm repo add longhorn https://charts.longhorn.io

helm repo update

helm upgrade longhorn longhorn/longhorn --install --namespace longhorn-system --create-namespace --values values.yml --version 1.4.1
```
