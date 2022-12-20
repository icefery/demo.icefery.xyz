# MySQL

## Install

### Docker

```bash
mysql -p
```

```sql
create database if not exists demo;

create user 'demo'@'%' identified by 'demo';

revoke all on *.* from 'demo'@'%';

grant all on demo.* to 'demo'@'%';

grant select on *.* to 'demo'@'%';

flush privileges;
```

```sql
use mysql;

delete from user where user = 'root' and host = '%';
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
stop replica;

change replication source to
    source_host='192.192.192.101',
    source_port=3306,
    source_user='root',
    source_password='root',
    source_log_file=<File>,
    source_log_pos=<Position>;

start replica;

show replica status;
```
