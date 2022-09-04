## 基础服务

#### 安装 DNSmasq

```bash
apt install -y dnsmasq

# 配置泛解析
echo "address=/dev.icefery.xyz/192.192.192.6" > /etc/dnsmasq.conf
```

## 常见问题

#### Failed to allocate directory watch: Too many open files

> https://zhuanlan.zhihu.com/p/222506941

```bash
cat >> /etc/sysctl.conf <<- "EOF"
fs.inotify.max_user_instances=512
fs.inotify.max_user_watches=262144
EOF

sysctl -p
```