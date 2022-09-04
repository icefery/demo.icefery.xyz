### 安装

```bash
apt-get install clamav clamav-daemon
```

### 升级病毒库

```bash
systemctl stop clamav-freshclam

freshclam

systemctl start clamav-freshclam
```

### 扫描

```bash
clamscan --infected  –-recursive --remove /
```
