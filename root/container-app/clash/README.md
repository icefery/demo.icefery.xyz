# Clash

## 快速开始

### 终端设置代理

```shell
tee > /etc/profile.d/custom.sh <<- "EOF"
function start-proxy() {
    export http_proxy=http://127.0.0.1:7890
    export https_proxy=${http_proxy}
    export no_proxy=127.0.0.0/8,10.0.0.0/8,172.16.0.0/12,192.168.0.0/16
}
function stop-proxy() {
    unset http_proxy
    unset https_proxy
    unset no_proxy
}
EOF

chmod +x /etc/profile.d/custom.sh

source /etc/profile
```

### Systemd 设置代理

```shell
mkdir -p /etc/systemd/system/docker.service.d

tee /etc/systemd/system/docker.service.d/proxy.conf > /dev/null <<- "EOF"
[Service]
Environment="HTTP_PROXY=http://192.168.8.10:7890"
Environment="HTTPS_PROXY=http://192.168.8.10:7890"
Environment="NO_PROXY=127.0.0.0/8,10.0.0.0/8,172.16.0.0/12,192.168.0.0/16"
EOF
```
