# Go

## 版本管理工具 g

> -   https://github.com/voidint/g ![](https://img.shields.io/github/stars/voidint/g) ![](https://img.shields.io/github/forks/voidint/g) ![](https://img.shields.io/github/release/voidint/g)

### 手动安装

```shell
export G_EXPERIMENTAL="true"
export G_HOME="/opt/env/g"
export G_MIRROR="https://mirrors.aliyun.com/golang"
export GOROOT="/opt/env/g/go"
export GOPATH="/opt/env/gopath"
export GOPROXY="https://goproxy.cn,direct"
export GO111MODULE="on"
export PATH="${G_HOME}/bin:${GOROOT}/bin:${GOPATH}/bin:${PATH}"

mkdir -p "${G_HOME}/bin"

tar -zxvf ~/Downloads/g1.6.0.darwin-arm64.tar.gz -C "${G_HOME}/bin"

sudo xattr -dr com.apple.quarantine "${G_HOME}/bin/g"
```

### 常用命令

```shell
g ls

g ls-remote

g install 1.22.2

g use 1.22.2

g self update
```
