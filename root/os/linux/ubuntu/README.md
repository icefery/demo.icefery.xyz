# Ubuntu

## 一、安装

### 1.1 登录配置

```shell
# 启用 root 用户通过密码登录 SSH
sudo sed -i '/PermitRootLogin/c PermitRootLogin yes' /etc/ssh/sshd_config
sudo systemctl restart ssh

# 设置 root 用户密码
sudo passwd root
```

### 1.2 网络配置

```shell
# 参考 centos7 配置本地 hosts
sudo tee /etc/hosts > /dev/null <<- "EOF"
127.0.0.1 localhost localhost.localdomain localhost4 localhost4.localdomain4
::1       localhost localhost.localdomain localhost6 localhost6.localdomain6
EOF

# 禁用 ipv6
sudo tee /etc/sysctl.conf > /dev/null <<- "EOF"
net.ipv6.conf.all.disable_ipv6=1
net.ipv6.conf.default.disable_ipv6=1
net.ipv6.conf.lo.disable_ipv6=1
EOF
sudo sysctl -p

# GRUB 禁用 ipv6
sudo sed -i 's GRUB_CMDLINE_LINUX="" GRUB_CMDLINE_LINUX="ipv6.disable=1" g' /etc/default/grub
sudo update-grub

# 配置 ip gateway dns
sudo vim /etc/netplan/00-installer-config.yaml
sudo netplan apply
```

```yaml
network:
  ethernets:
    ens33:
      addresses:
        - 192.168.8.101/24
      nameservers:
        addresses:
          - 114.114.114.114
        search: []
      routes:
        - to: default
          via: 192.168.8.1
  version: 2
```

### 1.3 应用配置

```shell
# 卸载 snap
sudo apt remove snapd --purge --autoremove -y

# 解决 systemd-resolve 占用 53 端口
sudo sed -i -e '/#DNSStubListener=/c DNSStubListener=no' /etc/systemd/resolved.conf
sudo ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf
sudo systemctl restart systemd-resolved

# 配置 vim
sudo tee /etc/vim/vimrc.local > /dev/null <<- "EOF"
set autoread
set cursorline
set nopaste
set expandtab
set tabstop=4
set softtabstop=4
set shiftwidth=4
set laststatus=2
EOF
```

### 1.4 更新配置

```shell
# 换源
sudo tee /etc/apt/sources.list <<- "EOF"
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu-ports/ jammy           main restricted universe multiverse
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu-ports/ jammy-security  main restricted universe multiverse
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu-ports/ jammy-updates   main restricted universe multiverse
deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu-ports/ jammy-backports main restricted universe multiverse
EOF

# 更新
sudo apt update
sudo apt full-upgrade -y
sudo apt list --upgradable 2> /dev/null | grep -v 'Listing' | cut -d '/' -f 1 | xargs sudo apt upgrade -y
```

### 1.5 系统配置

```shell
# 禁用 swap
sudo swapoff -a
sudo sed -i '/swap/s/^\(.*\)$/#\1/g' /etc/fstab
sudo rm -rf /swap.img

# 配置最大文件句柄数
tee /etc/security/limits.conf <<- "EOF"
*    soft nofile 102400
*    hard nofile 102400
root soft nofile 102400
root hard nofile 102400
EOF

# 重启
reboot
```

### 1.6 环境变量配置

```shell
# 删除用户默认配置
rm -rf ~/.bashrc .profile

# 全局配置
sudo tee /etc/profile.d/custom.sh <<- "EOF"
export PS1='[\[\e[01;32m\]\u\[\e[00m\]@\[\e[01;33m\]\h\[\e[00m\]:\[\e[01;32m\]\w\[\e[00m\]]\$ '
export TZ='Asia/Shanghai'
export TIME_STYLE='+%Y-%m-%d %H:%M:%S'

alias ll='ls -AlhF --color=auto'

function start_proxy() {
  export http_proxy=http://192.168.8.10:7890
  export https_proxy=$http_proxy
  export no_proxy=192.168.8.*
}
function stop_proxy() {
  unset http_proxy
  unset https_proxy
  unset no_proxy
}
EOF
sudo chmod 777 /etc/profile.d/custom.sh

# 刷新
source /etc/profile
```

## 二、VMWare

### 2.1 安装 VM Tools

```shell
sudo apt remove open-vm-tools --purge --autoremove -y
sudo apt install open-vm-tools -y
sudo apt install open-vm-tools-desktop -y
```

## 三、MySQL

### 3.1 安装 MySQL

```shell
# 安装
sudo apt search mysql-server
sudo apt install mysql-server -y

# 重置
sudo systemctl stop mysql
sudo rm -rf /var/lib/mysql/* /etc/mysql/*

# 配置
sudo tee /etc/mysql/my.cnf <<- "EOF"
[mysqld]
user                          = mysql
bind-address                  = 0.0.0.0
lower-case-table-names        = 1
default-time-zone             = +8:00
default-authentication-plugin = mysql_native_password
EOF

# 初始化
sudo mysqld --initialize

# 重启
sudo systemctl start mysql

# 登录
mysql -uroot -p
```

### 3.2 修改密码

```sql
-- 修改密码
alter user 'user'@'localhost' identified by 'root';

-- 配置可远程登录
use mysql;
select user, host, plugin from user;
update user set host = '%' where user = 'root';

-- 刷新权限
flush privileges;
```
