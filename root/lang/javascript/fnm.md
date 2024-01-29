# fnm

[![](https://img.shields.io/github/stars/Schniz/fnm.svg)](https://github.com/Schniz/fnm)

## 安装

```shell
curl -L -O "$(curl -s "https://api.github.com/repos/Schniz/fnm/releases/latest" | jq -r '.assets[] | select(.name | test("fnm-linux.zip$")) | .browser_download_url')"

mkdir -p /opt/env/fnm

unzip -d /opt/env/fnm fnm-linux.zip

chmod +x /opt/env/fnm
```

```shell
export FNM_DIR="/opt/env/fnm"
export FNM_NODE_DIST_MIRROR="https://mirrors.tuna.tsinghua.edu.cn/nodejs-release"
export PATH="${FNM_DIR}:$PATH"

if [[ $(command -v "fnm") ]]; then
    eval "$(fnm env --use-on-cd)"
    eval "$(fnm completions --shell bash)"
fi
```

```shell
fnm install --lts
```
