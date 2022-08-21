# Clash

## 前置条件

### 下载节点配置文件

```bash
mkdir -p /d/mount/clash/

wget -O /d/mount/clash/config.yaml <订阅链接>
```

## 设置代理

### 终端

```bash
cat >> /etc/custom.sh <<- "EOF"
function start-proxy() {
  export http_proxy=http://127.0.0.1:7890
  export https_proxy=${http_proxy}
  export no_proxy="127.0.0.1,192.168.0.0/16,10.0.0.0/8,172.16.0.0/12,100.64.0.0/10,17.0.0.0/8,localhost,*.local,169.254.0.0/16,224.0.0.0/4,240.0.0.0/4"
}
function stop-proxy() {
  unset http_proxy
  unset https_proxy
  unset no_proxy
}
EOF

source /etc/profile
```

### systemd

```bash
mkdir -p /etc/systemd/system/docker.service.d

cat > /etc/systemd/system/docker.service.d/proxy.conf <<- "EOF"
[Service]
Environment="HTTP_PROXY=http://127.0.0.1:7890"
Environment="HTTPS_PROXY=http://127.0.0.1:7890"
EOF
```
