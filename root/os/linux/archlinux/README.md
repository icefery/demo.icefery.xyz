# Arch

## 一、安装

### 1.1 网络配置

```shell
cat | sudo tee /etc/systemd/network/10-enp0s1.network > /dev/null <<- "EOF"
[Match]
Name=enp0s1

[Network]
Address=192.168.16.101/24
Gateway=192.168.16.1
DNS=119.29.29.29
EOF
```

### 1.2 常用工具

> 查看某个命令来自于哪个包：`pacman -Qo nc`。

```shell
packages=(
    bat
    bind-tools
    fd
    htop
    ripgrep
    unzip
)
sudo pacman -Sy ${packages[@]}
```
