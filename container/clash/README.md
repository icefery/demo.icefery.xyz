# Clash

## Prerequisites

```bash
mkdir -p /d/mount/clash/

wget -O /d/mount/clash/config.yaml <URL>
```

## Install

### Docker

```bash
docker network create local

docker-compose up -d
```

### kubectl

```bash
kubectl create namespace local

kubectl apply -f clash.yaml
```

## Proxy

### Terminal function

```bash
function start-proxy() {
    export http_proxy=http://192.192.192.10:7890
    export https_proxy=http://192.192.192.10:7890
    export no_proxy=localhost,192.192.192.10,192.192.192.0/24
}
function stop-proxy() {
    unset http_proxy
    unset https_proxy
    unset no_proxy
}
```

### Systemd

```bash
mkdir -p /etc/systemd/system/docker.service.d

cat <<- EOF > /etc/systemd/system/docker.service.d/proxy.conf
[Service]
Environment="HTTP_PROXY=http://192.192.192.10:7890"
Environment="HTTPS_PROXY=http://192.192.192.10:7890"
EOF
```