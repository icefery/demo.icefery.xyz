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

## Replication

### Master

```sql
show master status;
```

### Slave

```sql
stop slave;

change master to
    master_host='192.192.192.101',
    master_port=3306,
    master_user='root',
    master_password='root',
    master_log_file=<File>,
    master_log_pos=<Position>;

start slave;

show slave status;
```
