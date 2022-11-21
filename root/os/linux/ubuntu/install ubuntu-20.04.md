### 网络配置

- `/etc/netplan/00-installer-config.yaml`

  > YAML Flow 数组语法中 IP 需要加引号。

  ```yaml
  network:
    ethernets:
      ens33:
        addresses: ['192.168.137.6/24']
        gateway4: 192.168.137.1
        nameservers:
          addresses: ['192.168.137.1']
    version: 2
  ```

### 换源

- `/etc/apt/sources.list`

  ```bash
  deb https://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
  deb https://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
  deb https://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
  deb https://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
  ```

### 禁用防火墙

```bash
sudo ufw status

sudo ufw disable
```

### 启用 SSH 登录 `root`

```bash
sudo passwd root

sudo sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

sudo systemctl restart ssh
```

### 美化 PS1

- `/etc/bash.bashrc`

  ```bash
  export PS1='[\[\e[01;32m\]\u\[\e[00m\]@\[\e[01;33m\]\h\[\e[00m\]:\[\e[01;34m\]\w\[\e[00m\]]\$ '
  ```

### 禁用 SSH 登录欢迎信息

- 注释 `/etc/pam.d/sshd` 以下内容

  ```bash
  session    optional     pam_motd.so  motd=/run/motd.dynamic
  session    optional     pam_motd.so  noupdate
  ```

### 安装 VM Tools

```bash
sudo apt-get autoremove open-vm-tools

sudo apt-get install open-vm-tools

sudo apt-get install open-vm-tools-desktop
```
