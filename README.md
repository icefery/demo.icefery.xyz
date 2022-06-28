# DEV

## 克隆

```bash
mkdir -p /d

chmod 777 -R /d

git clone https://github.com/icefery/dev.icefery.xyz.git /d/dev --branch=main --depth=1

cp -r /d/dev/mount /d
```

## Containerd

```bash
TAG=$(wget -q -O- -t 1 -T 2 https://api.github.com/repos/containerd/nerdctl/releases/latest | jq -r .tag_name)

wget "https://github.com/containerd/nerdctl/releases/download/${TAG}/nerdctl-full-${TAG:1}-linux-$(uname -m).tar.gz"

tar Cxzvvf /usr/local "nerdctl-full-${TAG:1}-linux-$(uname -m).tar.gz"

mkdir -p /etc/containerd

cat <<-EOF >>/etc/containerd/config.toml
[plugins.cri.registry.mirrors."docker.io"]
  endpoint = ["https://uwk49ut2.mirror.aliyuncs.com"]
EOF

systemctl enable --now containerd

nerdctl completion bash >/etc/bash_completion.d/nerdctl

source /etc/profile
```

## K3S

```bash
/usr/local/bin/k3s-uninstall.sh

/usr/local/bin/k3s-agent-uninstall.sh

curl -sfL https://get.k3s.io | sh -s - \
  --kube-apiserver-arg service-node-port-range=1-65535 \
  --disable coredns \
  --disable servicelb \
  --disable traefik \
  --disable local-storage \
  --disable metrics-server

cat <<-EOF >>/etc/bash.bashrc
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
EOF

kubectl completion bash | tee /etc/bash_completion.d/kubectl >/dev/null

source /etc/profile

cp /var/lib/rancher/k3s/agent/etc/containerd/config.toml /var/lib/rancher/k3s/agent/etc/containerd/config.toml.tmpl

cat <<-EOF >>/var/lib/rancher/k3s/agent/etc/containerd/config.toml.tmpl
[plugins.cri.registry.mirrors."docker.io"]
  endpoint = ["https://uwk49ut2.mirror.aliyuncs.com"]
EOF

systemctl daemon-load

systemctl restart k3s
```

## Helm

```bash
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash

helm completion bash >/etc/bash_completion.d/helm

source /etc/profile
```
