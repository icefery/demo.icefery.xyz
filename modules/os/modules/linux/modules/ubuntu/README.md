# Ubuntu

## 收藏

- Failed to connect to https://changelogs.ubuntu.com/meta-release-lts. Check your Internet connection or proxy settings

  ```bash
  rm /var/lib/ubuntu-release-upgrader/release-upgrade-available
  ```

- ubuntu 上 `/dev/loop0` 到 `/dev/loop7` 占到 100% 的处理

  ```bash
  df -h

  sudo apt autoremove --purge snapd
  ```
