### 下载最新版 nerdctl

```shell
REPO="containerd/nerdctl"
TAG=$(curl -fsSL "https://api.github.com/repos/${REPO}/releases/latest" | jq -r ".tag_name")
ASSET="nerdctl-full-${TAG//v/}-linux-$(dpkg --print-architecture).tar.gz"

wget "https://github.com/${REPO}/releases/download/${TAG}/${ASSET}" -O "${ASSET}"

tar Cxzvvf /usr/local "${ASSET}"

systemctl enable --now containerd

systemctl enable --now buildkit

nerdctl completion bash > /etc/bash_completion.d/nerdctl

source /etc/profile
```
