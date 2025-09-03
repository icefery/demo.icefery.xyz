# Longhorn

## 前置条件

> https://longhorn.io/docs/1.5.3/deploy/install/#installation-requirements

```shell
# 环境检查
sudo pacman -S --noconfirm jq coreutils
curl -sSfL https://raw.githubusercontent.com/longhorn/longhorn/v1.5.3/scripts/environment_check.sh | bash

# 自动安装 open-iscsi 和 nfs-client
kubectl apply -f https://raw.githubusercontent.com/longhorn/longhorn/v1.5.3/deploy/prerequisite/longhorn-iscsi-installation.yaml
kubectl apply -f https://raw.githubusercontent.com/longhorn/longhorn/v1.5.3/deploy/prerequisite/longhorn-nfs-installation.yaml

# 手动安装
sudo apt install open-iscsi nfs-common -y
sudo pacman -S --noconfirm open-iscsi nfs-utils
sudo systemctl enable iscsid --now
```
