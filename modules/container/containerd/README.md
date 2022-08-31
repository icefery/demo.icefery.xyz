# Containerd

## 安装

```bash
wget https://github.com/containerd/nerdctl/releases/download/v0.22.0/nerdctl-full-0.22.0-linux-amd64.tar.gz

tar Cxzvvf /usr/local nerdctl-full-0.22.0-linux-amd64.tar.gz

systemctl enable --now containerd

systemctl enable --now buildkit

nerdctl run --privileged --rm tonistiigi/binfmt --install all
```

### 查看默认配置

```bash
containerd config default
```

### 命令补全

```bash
nerdctl completion bash > /etc/bash_completion.d/nerdctl

source /etc/profile
```

### 设置镜像源

> - https://github.com/containerd/containerd/blob/main/docs/cri/registry.md
> - https://github.com/containerd/containerd/blob/main/docs/hosts.md

```bash
mkdir -p /etc/containerd/certs.d/docker.io

cat > /etc/containerd/certs.d/docker.io/hosts.toml <<- "EOF"
server = "https://registry-1.docker.io"
[host."https://uwk49ut2.mirror.aliyuncs.com"]
  capabilities = ["pull"]
EOF
```

```bash
mkdir -p /etc/containerd

cat > /etc/containerd/config.toml <<- "EOF"
version = 2

[plugins."io.containerd.grpc.v1.cri".registry]
  config_path = "/etc/containerd/certs.d"
EOF
```

## 设置守护进程代理

```bash
mkdir -p /etc/systemd/system/containerd.service.d

cat > /etc/systemd/system/containerd.service.d/proxy.conf <<- "EOF"
[Service]
Environment="HTTP_PROXY=http://192.192.192.10:7890"
Environment="HTTPS_PROXY=http://192.192.192.10:7890"
EOF

systemctl daemon-reload

systemctl restart containerd
```
