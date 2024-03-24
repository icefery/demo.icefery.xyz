# PostgreSQL

## Docker 安装 PostgreSQL

#### 创建用户和数据库

```shell
psql -U postgres
```

```sql
CREATE ROLE demo WITH LOGIN PASSWORD 'demo';

CREATE DATABASE demo OWNER demo;
```

```shell
sed -i 's/host all all all md5/host all demo all md5/' /var/lib/postgresql/data/pg_hba.conf
```

## Helm 安装 PostgreSQL

```shell
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade postgresql bitnami/postgresql --install --namespace postgresql --create-namespace --values values.yaml --version 11.7.6
```

## 常见问题

-   Desktop Desktop for Windows 不能映射数据卷。
