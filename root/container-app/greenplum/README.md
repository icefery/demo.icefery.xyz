# GreenPlum

## Docker

> https://hub.docker.com/r/andruche/greenplum

### 环境变量

```shell
SHELL=/bin/bash
MASTER_DATA_DIRECTORY=/data/master/gpsne-1/
PWD=/home/gpadmin/
LOGNAME=gpadmin
HOME=/home/gpadmin/
GPHOME=/usr/local/gpdb
PYTHONPATH=/usr/local/gpdb/lib/python
TERM=xterm
USER=gpadmin
SHLVL=1
LD_LIBRARY_PATH=/usr/local/gpdb/lib
PS1=\[\033[1;35m\][\u@\h \W]\[\033[0;31m\]$\[\033[0;37m\]
PATH=/usr/local/gpdb/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
MAIL=/var/mail/gpadmin
_=/usr/bin/env
```

### 修改密码

```shell
su -l -s /bin/bash gpadmin

psql -d postgres
```

```sql
alter user gpadmin with password 'gpadmin';
```
