# Rust

## 安装

```shell
export CARGO_HOME=/opt/env/cargo
export RUSTUP_HOME=/opt/env/rustup
export RUSTUP_DIST_SERVER=https://mirrors.tuna.tsinghua.edu.cn/rustup
export RUSTUP_UPDATE_ROOT=https://mirrors.tuna.tsinghua.edu.cn/rustup/rustup
export PATH=$CARGO_HOME/bin:$PATH

[[ -x rustup ]] && source <(rustup completions bash)

mkdir -pv ${CARGO_HOME}

cat <<- 'EOF' | tee ${CARGO_HOME}/config.toml > /dev/null
[source.tuna]
registry = "https://mirrors.tuna.tsinghua.edu.cn/git/crates.io-index.git"

[source.crates-io]
replace-with = "tuna"
EOF
```

```shell
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
```

## 收藏

#### [学习 Rust 你需要一个认知框架](https://zhuanlan.zhihu.com/p/494001676)