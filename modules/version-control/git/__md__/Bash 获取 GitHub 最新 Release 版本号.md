### 下载 nerdctl 最新版

```bash
TAG=$(curl -fsSL "https://api.github.com/repos/containerd/nerdctl/releases/latest" | jq -r ".tag_name")
VERSION=${TAG//v/}
ARCH=$(dpkg --print-architecture)

wget "https://github.com/containerd/nerdctl/releases/download/${TAG}/nerdctl-full-${VERSION}-linux-${ARCH}.tar.gz" -O "nerdctl-full-${VERSION}-linux-${ARCH}.tar.gz"

tar Cxzvvf /usr/local "nerdctl-full-${VERSION}-linux-${ARCH}.tar.gz"
```
