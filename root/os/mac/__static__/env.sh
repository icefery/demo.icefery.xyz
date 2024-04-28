export TZ="Asia/Shanghai"
export LANG="en_US.UTF-8"
export TIME_STYLE="+%Y-%m-%d %H:%M:%S"

# homebrew
export HOMEBREW_PREFIX="/opt/homebrew"
export HOMEBREW_INSTALL_FROM_API=1
export HOMEBREW_API_DOMAIN="https://mirrors.tuna.tsinghua.edu.cn/homebrew-bottles/api"
export HOMEBREW_BOTTLE_DOMAIN="https://mirrors.tuna.tsinghua.edu.cn/homebrew-bottles"
export HOMEBREW_BREW_GIT_REMOTE="https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/brew.git"
export HOMEBREW_CORE_GIT_REMOTE="https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/homebrew-core.git"
export HOMEBREW_PIP_INDEX_URL="https://pypi.tuna.tsinghua.edu.cn/simple"
export PATH="${HOMEBREW_PREFIX}/bin:${HOMEBREW_PREFIX}/sbin:${PATH}"

# python | miniconda
export CONDA_PREFIX="/opt/env/miniconda"
export PATH="${CONDA_PREFIX}/bin:${PATH}"

# java | maven | gradle
export JAVA_HOME="/opt/env/jdk-8"
export M2_HOME="/opt/env/maven"
export GRADLE_HOME="/opt/env/gradle"
export PATH="${JAVA_HOME}/bin:${M2_HOME}/bin:${GRADLE_HOME}/bin:${PATH}"

# node | fnm
export FNM_DIR="/opt/env/fnm"
export FNM_NODE_DIST_MIRROR="https://mirrors.tuna.tsinghua.edu.cn/nodejs-release"
export NODE_HOME="${FNM_DIR}/alias/default"
export PATH="${FNM_DIR}/bin:${NODE_HOME}/bin:${PATH}"

# go | g
export G_EXPERIMENTAL="true"
export G_HOME="/opt/env/g"
export G_MIRROR="https://mirrors.aliyun.com/golang"
export GOROOT="/opt/env/g/go"
export GOPATH="/opt/env/gopath"
export GOPROXY="https://goproxy.cn,direct"
export GO111MODULE="on"
export PATH="${G_HOME}/bin:${GOROOT}/bin:${GOPATH}/bin:${PATH}"

# rust | cargo | rustup
export RUSTUP_HOME="/opt/env/rustup"
export CARGO_HOME="/opt/env/cargo"
export RUSTUP_DIST_SERVER="https://mirrors.tuna.tsinghua.edu.cn/rustup"
export RUSTUP_UPDATE_ROOT="https://mirrors.tuna.tsinghua.edu.cn/rustup/rustup"
export PATH="${RUSTUP_HOME}/bin:${CARGO_HOME}/bin:${PATH}"

function start_proxy() {
    export http_proxy="http://127.0.0.1:7890"
    export https_proxy="${http_proxy}"
}

function stop_proxy() {
    unset http_proxy
    unset https_proxy
}

function config_alias() {
    if [[ -n ${BASH_VERSION} ]]; then
        alias ll="ls -l -h -A -F"
    elif [[ -n ${ZSH_VERSION} ]]; then
        alias ll="ls -l -h -A -G -D '%Y-%m-%d %H:%M:%S'"
    fi
}

function config_homebrew() {
    if [[ $(command -v brew) ]]; then
        eval "$(brew shellenv)"
        if [[ -n ${BASH_VERSION} ]]; then
            HOMEBREW_PREFIX="$(brew --prefix)"
            if [[ -r "${HOMEBREW_PREFIX}/etc/profile.d/bash_completion.sh" ]]; then
                source "${HOMEBREW_PREFIX}/etc/profile.d/bash_completion.sh"
            else
                for COMPLETION in "${HOMEBREW_PREFIX}/etc/bash_completion.d/"*; do
                    [[ -r ${COMPLETION} ]] && source "${COMPLETION}"
                done
            fi
        elif [[ -n ${ZSH_VERSION} ]]; then
            FPATH="$(brew --prefix)/share/zsh/site-functions:${FPATH}"
            autoload -Uz compinit
            compinit
        fi
    fi
}

function config_miniconda() {
    if [[ $(command -v conda) ]]; then
        if [[ -n ${BASH_VERSION} ]]; then
            eval "$(conda shell.bash hook 2> /dev/null)"
        elif [[ -n ${ZSH_VERSION} ]]; then
            eval "$(conda shell.zsh hook 2> /dev/null)"
        fi
    fi
    if [[ ! -f ~/.pip/pip.conf ]]; then
        mkdir -p ~/.pip
        cat <<- 'EOF' | tee ~/.pip/pip.conf > /dev/null
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
EOF
    fi
}

function config_fnm() {
    if [[ $(command -v fnm) ]]; then
        eval "$(fnm env)"
        if [[ -n ${BASH_VERSION} ]]; then
            eval "$(fnm completions --shell bash)"
        elif [[ -n ${ZSH_VERSION} ]]; then
            eval "$(fnm completions --shell zsh)"
        fi
    fi
}

function config_cargo() {
    if [[ $(command -v cargo) ]]; then
        if [[ -n ${BASH_VERSION} ]]; then
            eval "$(rustup completions bash)"
            eval "$(rustup completions bash cargo)"
        elif [[ -n ${ZSH_VERSION} ]]; then
            if [[ ! -f ~/.zfunc/_rustup ]]; then
                mkdir -p ~/.zfunc
                rustup completions zsh > ~/.zfunc/_rustup
                rustup completions zsh cargo > ~/.zfunc/_cargo
            fi
        fi
    fi
    if [[ ! -f "${CARGO_HOME}/config.toml" ]]; then
        mkdir -p "${CARGO_HOME}"
        cat <<- 'EOF' | tee "${CARGO_HOME}/config.toml" > /dev/null
[source.tuna]
registry = "https://mirrors.tuna.tsinghua.edu.cn/git/crates.io-index.git"
[source.crates-io]
replace-with = "tuna"
EOF
    fi
}

config_alias
config_homebrew
config_miniconda
config_fnm
config_cargo
