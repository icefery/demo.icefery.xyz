export TZ="Asia/Shanghai"
export LANG="en_US.UTF-8"
export TIME_STYLE="+%Y-%m-%d %H:%M:%S"

# java | maven
export JAVA_HOME="/opt/env/jdk-8"
export M2_HOME="/opt/env/maven"
export PATH="$JAVA_HOME/bin:$M2_HOME/bin:$PATH"

# node | fnm
export FNM_DIR="/opt/env/fnm"
export FNM_NODE_DIST_MIRROR="https://mirrors.tuna.tsinghua.edu.cn/nodejs-release"

# go
export GOROOT="/opt/env/go"
export GOPATH="/opt/env/gopath"
export GOPROXY="https://goproxy.cn,direct"
export PATH="$GOROOT/bin:$GOPATH/bin:$PATH"

# rust
export RUSTUP_HOME=/opt/env/rustup
export CARGO_HOME=/opt/env/cargo
export RUSTUP_DIST_SERVER=https://mirrors.tuna.tsinghua.edu.cn/rustup
export RUSTUP_UPDATE_ROOT=https://mirrors.tuna.tsinghua.edu.cn/rustup/rustup
export PATH="$RUSTUP_HOME/bin:$CARGO_HOME/bin:$PATH"

function start_proxy() {
    export http_proxy=http://127.0.0.1:7890
    export https_proxy=$http_proxy
}

function stop_proxy() {
    unset http_proxy
    unset https_proxy
}

function config_homebrew() {
    [[ -x /opt/homebrew/bin/brew ]] && eval "$(/opt/homebrew/bin/brew shellenv)"
}

function config_miniconda() {
    [[ -x /opt/env/miniconda/bin/conda ]] && eval "$(/opt/env/miniconda/bin/conda shell.zsh hook 2> /dev/null)"
}

function config_fnm() {
    if [[ -x /opt/homebrew/bin/fnm ]]; then
        eval "$(/opt/homebrew/bin/fnm env --use-on-cd)"
        eval "$(/opt/homebrew/bin/fnm completions --shell bash)"
    fi
}

function config_cargo() {
    if [[ ! -f "${CARGO_HOME}/config.toml" ]]; then
        cat <<- 'EOF' | tee "${CARGO_HOME}/config.toml" > /dev/null
[source.tuna]
registry = "https://mirrors.tuna.tsinghua.edu.cn/git/crates.io-index.git"

[source.crates-io]
replace-with = "tuna"
EOF
    fi
}

function config_pip() {
    if [[ ! -f "~/.pip/pip.conf" ]]; then
        mkdir -p ~/.pip
        cat <<- 'EOF' | tee ~/.pip/pip.conf > /dev/null
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
EOF
    fi
}

config_homebrew

config_miniconda

config_fnm

config_cargo

config_pip
