# MySQL

## Install

### Docker

```bash
mysql -p
```

```sql
CREATE
DATABASE IF NOT EXISTS demo;

CREATE
USER 'demo'@'%' IDENTIFIED BY 'demo';

REVOKE ALL ON *.* FROM 'demo'@'%';

GRANT
ALL
ON demo.* TO 'demo'@'%';

GRANT
SELECT
ON *.* TO 'demo'@'%';

FLUSH
PRIVILEGES;
```

```sql
USE
mysql;

DELETE
FROM user
WHERE user = 'root'
  AND host = '%';
```

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade mysql bitnami/mysql --install --namespace mysql --create-namespace --values values.yaml --version 9.2.6
```