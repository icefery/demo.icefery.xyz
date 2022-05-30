## 卸载

```bash
/usr/local/bin/k3s-uninstall.sh

/usr/local/bin/k3s-agent-uninstall.sh
```

## 安装

```bash
curl -sfL https://get.k3s.io | sh -s - \
  --kube-apiserver-arg service-node-port-range=1-65535 \
  --disable traefik \
  --disable metrics-server
```

## 设置 `KUBECONFIG`

```bash
cat <<- EOF >> /etc/profile
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
EOF

source /etc/profile
```

## 设置 Containerd 镜像源

```bash
cp /var/lib/rancher/k3s/agent/etc/containerd/config.toml /var/lib/rancher/k3s/agent/etc/containerd/config.toml.tmpl

cat <<- EOF >> /var/lib/rancher/k3s/agent/etc/containerd/config.toml.tmpl
[plugins.cri.registry.mirrors]
  [plugins.cri.registry.mirrors."docker.io"]
    endpoint = ["https://uwk49ut2.mirror.aliyuncs.com"]
EOF

systemctl restart k3s
```

## `kubectl` 自动补全

```bash
kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null

source /etc/profile
```
