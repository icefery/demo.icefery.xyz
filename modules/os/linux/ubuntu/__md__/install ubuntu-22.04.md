## Install

#### Mirror address

```bash
https://mirrors.aliyun.com/ubuntu
```

## Config

#### SSH

```bash
sudo sed -i '/PermitRootLogin/c PermitRootLogin yes' /etc/ssh/sshd_config

sudo systemctl restart ssh
```

#### Swap

```bash
sudo swapoff -a

sudo sed -i '/swap/s/^\(.*\)$/#\1/g' /etc/fstab

reboot
```

#### Mirror

```bash
cat <<-EOF >/etc/apt/sources.list
deb http://mirrors.aliyun.com/ubuntu/ jammy           main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-security  main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-updates   main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ jammy-backports main restricted universe multiverse
EOF

apt update && apt full-upgrade -y
```

#### Env

```bash
cat <<-EOF >>/etc/bash.bashrc
alias ls='ls -hAF --color=auto --time-style=long-iso'
alias ll='ls -l'
alias drop-cache='echo 3 > /proc/sys/vm/drop_caches drop_caches'
export PS1='[\[\e[01;32m\]\u\[\e[00m\]@\[\e[01;33m\]\h\[\e[00m\]:\[\e[01;32m\]\w\[\e[00m\]]\$ '
export TZ=Asia/Shanghai
EOF

source /etc/profile
```

#### Proxy

```bash
export https_proxy=http://192.192.192.10:7890
export http_proxy=http://192.192.192.10:7890
```

#### DNS

```bash
apt remove bind9 --purge --autoremove -y

sed -i -e '/#DNSStubListener=/c DNSStubListener=no' /etc/systemd/resolved.conf

ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf

systemctl restart systemd-resolved
```