# Debian

## 安装

### 静态 IP

```shell
cat > /etc/network/interfaces <<- EOF
auto lo
iface lo inet loopback

auto ens33
iface ens33 inet static
  address 192.168.9.100
  netmask 255.255.255.0
  gateway 192.168.9.254
EOF

systemctl restart networking
```

## 基础服务

### DNSmasq

```shell
apt install -y dnsmasq

# 配置泛解析
echo "address=/dev.icefery.xyz/192.192.192.6" > /etc/dnsmasq.conf
```

## 常见问题

### Failed to allocate directory watch: Too many open files

> https://zhuanlan.zhihu.com/p/222506941

```shell
cat >> /etc/sysctl.conf <<- "EOF"
fs.inotify.max_user_instances=512
fs.inotify.max_user_watches=262144
EOF

sysctl -p
```

### FATA[0000] get apparmor_parser version: apparmor_parser resolves to executable in current directory (./apparmor_parser)

> [解决 FATA[0000] Get http:///var/run... sudo apt-get install apparmor 好神奇](https://blog.csdn.net/qq_40088463/article/details/110863372)

```shell
apt install apparmor
```
