```bash
mysql -p
```

```sql
CREATE DATABASE IF NOT EXISTS icefery;

CREATE USER 'icefery'@'%' IDENTIFIED BY 'icefery';

REVOKE ALL ON *.* FROM 'icefery'@'%';

GRANT ALL ON icefery.* TO 'icefery'@'%';

GRANT SELECT ON *.* TO 'icefery'@'%';

FLUSH PRIVILEGES;
```

```sql
USE mysql;

DELETE FROM user WHERE user='root' AND host='%';
```
