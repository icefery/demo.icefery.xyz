# PostgreSQL

## Install

### Docker

```bash
psql -U postgres
```

```postgresql
CREATE ROLE demo WITH LOGIN PASSWORD 'demo';

CREATE DATABASE demo OWNER demo;
```

```bash
sed -i 's/host all all all md5/host all demo all md5/' /var/lib/postgresql/data/pg_hba.conf
```

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade postgresql bitnami/postgresql \
    --install \
    --create-namespace \
    --namespace postgresql \
    --version 11.6.7 \
    --values values.yaml
```

## X

- Desktop Desktop for Windows 不能映射数据卷。