# MySQL

## Install

### Docker

```bash
mysql -p
```

```sql
CREATE DATABASE IF NOT EXISTS demo;

CREATE USER 'demo'@'%' IDENTIFIED BY 'demo';

REVOKE ALL ON *.* FROM 'demo'@'%';

GRANT ALL ON demo.* TO 'demo'@'%';

GRANT SELECT ON *.* TO 'demo'@'%';

FLUSH PRIVILEGES;
```

```sql
USE mysql;

DELETE FROM user WHERE user = 'root' AND host = '%';
```

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade mysql bitnami/mysql \
    --install \
    --create-namespace \
    --namespace mysql \
    --version 9.1.7 \
    --values values.yaml
```