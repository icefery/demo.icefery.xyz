# OpenEBS

## 安装

```bash
helm repo add openebs https://openebs.github.io/charts

helm repo update

helm upgrade openebs openebs/openebs --install --namespace openebs --create-namespace --values values.yaml --version 3.3.0
```
