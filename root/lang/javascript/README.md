# JavaScript

## 收藏

-   [npm && 串行任务的时候 cross-env 出现问题](https://segmentfault.com/q/1010000014421074)

-   [前端经典面试题: 从输入 URL 到页面加载发生了什么？](https://segmentfault.com/a/1190000006879700)

-   [可视化工具 D3 教程](https://blog.csdn.net/qq_31052401/article/details/93786425)

-   [node-sass 下载失败 解决方案 #24](https://github.com/PanJiaChen/vue-element-admin/issues/24)

-   [为什么 npm 要为每个项目单独安装一遍 node_modules？](https://www.jianshu.com/p/6359cc3ac3c6)

-   [electron-vue 跨平台桌面应用开发实战教程（七）——ffi 调用 C++（Windows 平台）](https://my.oschina.net/david1025/blog/3173842)

-   [NodeJS VM 和 VM2 沙箱逃逸](https://xz.aliyun.com/t/11859)

## 版本管理工具 `fnm`

> -   https://github.com/Schniz/fnm > ![](https://img.shields.io/github/stars/Schniz/fnm) > ![](https://img.shields.io/github/forks/Schniz/fnm) > ![](https://img.shields.io/github/release/Schniz/fnm)

### 手动安装

```shell
export FNM_DIR="/opt/env/fnm"
export FNM_NODE_DIST_MIRROR="https://mirrors.tuna.tsinghua.edu.cn/nodejs-release"
export NODE_HOME="${FNM_DIR}/alias/default"
export PATH="${FNM_DIR}/bin:${NODE_HOME}/bin:${PATH}"

curl -L -O "$(curl -fsSL "https://api.github.com/repos/Schniz/fnm/releases/latest" | jq -r '.assets[] | select(.name | test("fnm-linux.zip$")) | .browser_download_url')"

mkdir -p "${FNM_DIR}/bin"

unzip fnm-linux.zip "${FNM_DIR}/bin"

chmod +x "${FNM_DIR}/bin/fnm"
```

> Windows 上 Node.js 没有 `bin` 目录，`PATH` 环境变量配置为 `%NODE_HOME%`。

### 配置 Shell

```shell
function config_fnm() {
    if [[ $(command -v fnm) ]]; then
        eval "$(fnm env)"
        eval "$(fnm completions --shell zsh)"
    fi
}
```

### 常用命令

```shell
fnm ls

fnm ls-remote

fnm install --tls

fnm use v20.12.2

fnm default v20.12.2
```
