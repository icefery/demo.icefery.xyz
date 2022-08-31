# Ubuntu

## 收藏

#### Failed to connect to https://changelogs.ubuntu.com/meta-release-lts. Check your Internet connection or proxy settings

```bash
rm /var/lib/ubuntu-release-upgrader/release-upgrade-available
```

#### ubuntu 上 `/dev/loop0` 到 `/dev/loop7` 占到 100% 的处理

```bash
df -h

sudo apt autoremove --purge snapd
```

#### `too many open files`

```bash
*    soft nofile 65535
*    hard nofile 65535
root soft nofile 65535
root hard nofile 65535
```
