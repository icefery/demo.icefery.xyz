# Kafka

## Install

### Docker

```shell
mkdir -p /d/mount/kafka

chmod 777 -R /d/mount/kafka
```

### Helm

```shell
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade kafka bitnami/kafka \
    --install \
    --create-namespace \
    --namespace kafka \
    --version 18.0.0 \
    --values values.yaml
```
