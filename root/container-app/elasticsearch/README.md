# Elasticsearch

## Install

### Docker

```bash
bin/elasticsearch-setup-passwords interactive
```

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade elasticsearch bitnami/elasticsearch --install --namespace elasticsearch --create-namespace --values values.yaml --version 19.1.11
```
