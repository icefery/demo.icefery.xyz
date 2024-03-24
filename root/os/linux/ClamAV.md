### 安装

```shell
apt-get install clamav clamav-daemon
```

### 升级病毒库

```shell
systemctl stop clamav-freshclam

freshclam

systemctl start clamav-freshclam
```

### 扫描

```shell
clamscan --infected  –-recursive --remove /
```
