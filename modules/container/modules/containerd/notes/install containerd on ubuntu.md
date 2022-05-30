## 安装

```bash
wget https://github.com/containerd/nerdctl/releases/download/v0.20.0/nerdctl-full-0.20.0-linux-amd64.tar.gz

tar Cxzvvf /usr/local nerdctl-full-0.20.0-linux-amd64.tar.gz

systemctl enable --now containerd
```

## 暴露默认配置

```bash
mkdir -p /etc/containerd/ containerd config default > /etc/containerd/config.toml
```

## 命令补全

```bash
nerdctl completion bash > /etc/bash_completion.d/nerdctl

source /etc/profile
```

## 设置镜像源

```bash
cat <<-EOF >> /etc/containerd/config.toml
[plugins.cri.registry.mirrors]
  [plugins.cri.registry.mirrors."docker.io"]
    endpoint = ["https://uwk49ut2.mirror.aliyuncs.com"]
EOF
```

## 设置守护进程代理

```bash
mkdir -p /etc/systemd/system/containerd.service.d

mkdir -p /etc/systemd/system/containerd.service.d

cat <<- EOF > /etc/systemd/system/containerd.service.d/proxy.conf
[Service]
Environment="HTTP_PROXY=http://127.0.0.1:7890"
Environment="HTTPS_PROXY=http://127.0.0.1:7890"
EOF

systemctl daemon-reload && systemctl restart containerd
```
