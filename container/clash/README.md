# Clash

## 前置条件

### 下载节点文件

```bash
mkdir -p /d/mount/clash/

wget -O /d/mount/clash/config.yaml <URL>
```

## 设置代理

### 终端

```bash
function start-proxy() {
    export https_proxy=http://127.0.0.1:7890
    export http_proxy=http://127.0.0.1:7890
    export all_proxy=socks5://127.0.0.1:7891
    export no_proxy="127.0.0.1,192.168.0.0/16,10.0.0.0/8,172.16.0.0/12,100.64.0.0/10,17.0.0.0/8,localhost,*.local,169.254.0.0/16,224.0.0.0/4,240.0.0.0/4"
}
function stop-proxy() {
    unset http_proxy
    unset https_proxy
    unset all_proxy
    unset no_proxy
}
```

### systemd

```bash
mkdir -p /etc/systemd/system/docker.service.d

cat <<-EOF >/etc/systemd/system/docker.service.d/proxy.conf
[Service]
Environment="HTTP_PROXY=http://127.0.0.1:7890"
Environment="HTTPS_PROXY=http://127.0.0.1:7890"
EOF
```
