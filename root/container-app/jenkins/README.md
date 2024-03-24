# Jenkins

## Install

### Helm

```shell
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade jenkins bitnami/jenkins --install --namespace jenkins --create-namespace --values values-bitnami.yaml --version 11.0.0
```
