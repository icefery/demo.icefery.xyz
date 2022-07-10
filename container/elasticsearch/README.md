# Elasticsearch

## Install

### Docker

```bash
bin/elasticsearch-setup-passwords interactive
```

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade elasticsearch bitnami/elasticsearch \
  --install \
  --create-namespace \
  --namespace elasticsearch \
  --version 18.2.13 \
  --values values.yaml
```