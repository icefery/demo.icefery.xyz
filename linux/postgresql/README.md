```bash
psql -U postgres
```

```sql
CREATE ROLE icefery WITH LOGIN PASSWORD 'icefery';

CREATE DATABASE icefery OWNER icefery;
```

```bash
sed -i 's/host all all all md5/host all icefery all md5/' /var/lib/postgresql/data/pg_hba.conf
```
