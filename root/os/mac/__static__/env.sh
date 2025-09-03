export TZ="Asia/Shanghai"
export LANG="en_US.UTF-8"
export TIME_STYLE="+%Y-%m-%d %H:%M:%S"

# env
ENV_OS="$(
    if [[ "$(uname -s)" == "Linux" ]]; then
        echo "linux"
    elif [[ $(uname -s) == "Darwin" ]]; then
        echo "darwin"
    elif [[ "$(uname -s)" == "MINGW"* || "$(uname -s)" == "CYGWIN"* ]]; then
        echo "windows"
    else
        echo "unknown"
        exit 1
    fi
)"

ENV_ARCH="$(
    if [[ "$(uname -m)" == "amd64" || "$(uname -m)" == "x86_64" ]]; then
        echo "amd64"
    elif [[ "$(uname -m)" == "arm64" || "$(uname -m)" == "aarch64" ]]; then
        echo "arm64"
    else
        echo "unknown"
        exit 1
    fi
)"

ENV_SHELL=$(
    if [[ -n ${BASH_VERSION} ]]; then
        echo "bash"
    elif [[ -n ${ZSH_VERSION} ]]; then
        echo "zsh"
    else
        echo "unknown"
        exit 1
    fi
)

# homebrew
export HOMEBREW_INSTALL_FROM_API=1
export HOMEBREW_API_DOMAIN="https://mirrors.tuna.tsinghua.edu.cn/homebrew-bottles/api"
export HOMEBREW_BOTTLE_DOMAIN="https://mirrors.tuna.tsinghua.edu.cn/homebrew-bottles"
export HOMEBREW_BREW_GIT_REMOTE="https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/brew.git"
export HOMEBREW_CORE_GIT_REMOTE="https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/homebrew-core.git"
export HOMEBREW_PIP_INDEX_URL="https://pypi.tuna.tsinghua.edu.cn/simple"

# python | miniconda | poetry
export CONDA_PREFIX="/opt/env/miniconda"
export POETRY_HOME="/opt/env/poetry"
export PATH="${CONDA_PREFIX}/bin:${POETRY_HOME}/bin:${PATH}"

# java | maven | gradle
export JAVA_HOME="/opt/env/jdk-21"
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
    case "${ENV_OS}" in
    linux | windows)
        alias ll="ls -l -h -A -F"
        ;;
    darwin)
        alias ll="ls -l -h -A -G -D '%Y-%m-%d %H:%M:%S'"
        ;;
    esac
}

function env_homebrew_install() {
    git clone --depth=1 "http://mirrors.tuna.tsinghua.edu.cn/git/homebrew/install.git" brew-install
    /bin/bash brew-install/install.sh
    rm -rf brew-install
}

function env_homebrew_config() {
    if [[ ${ENV_OS} == "linux" && -x "/home/linuxbrew/.linuxbrew/bin/brew" ]]; then
        eval "$(/home/linuxbrew/.linuxbrew/bin/brew shellenv)"
    elif [[ $ENV_OS == "darwin" && -x "/opt/homebrew/bin/brew" ]]; then
        eval "$(/opt/homebrew/bin/brew shellenv)"
    fi
    if [[ $(command -v brew) ]]; then
        eval "$(brew shellenv)"
        case "${ENV_SHELL}" in
        bash)
            if [[ -r "${HOMEBREW_PREFIX}/etc/profile.d/bash_completion.sh" ]]; then
                source "${HOMEBREW_PREFIX}/etc/profile.d/bash_completion.sh"
            else
                for completion in "${HOMEBREW_PREFIX}/etc/bash_completion.d/"*; do
                    [[ -r ${completion} ]] && source "${completion}"
                done
            fi
            ;;
        zsh)
            FPATH="${HOMEBREW_PREFIX}/share/zsh/site-functions:${FPATH}"
            autoload -Uz compinit && compinit
            ;;
        esac
    fi
}

function env_python_config() {
    if [[ ! -f ~/.pip/pip.conf ]]; then
        mkdir -p ~/.pip
        cat <<- "EOF" | tee ~/.pip/pip.conf > /dev/null
[global]
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
EOF
    fi
}

function env_python_miniconda_install() {
    local url=""
    case "${ENV_OS}/${ENV_ARCH}" in
    linux/amd64)
        url="https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh"
        ;;
    linux/arm64)
        url="https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-aarch64.sh"
        ;;
    darwin/amd64)
        url="https://repo.anaconda.com/miniconda/Miniconda3-latest-MacOSX-x86_64.sh"
        ;;
    darwin/arm64)
        url="https://repo.anaconda.com/miniconda/Miniconda3-latest-MacOSX-arm64.sh"
        ;;
    *)
        echo "unknown"
        exit 1
        ;;
    esac
    wget "${url}" -O /opt/env/install_miniconda.sh
    mkdir -p "${CONDA_PREFIX}"
    bash /opt/env/install_miniconda.sh -b -f -p "${CONDA_PREFIX}"
}

function env_python_miniconda_config() {
    if [[ $(command -v conda) ]]; then
        case "${ENV_SHELL}" in
        bash)
            eval "$(conda shell.bash hook 2> /dev/null)"
            ;;
        zsh)
            eval "$(conda shell.zsh hook 2> /dev/null)"
            ;;
        esac
    fi
}

function env_python_poetry_config() {
    if [[ $(command -v poetry) ]]; then
        case "${ENV_SHELL}" in
        bash)
            eval "$(poetry completions bash)"
            ;;
        zsh)
            mkdir -p ~/.zfunc
            poetry completions zsh > ~/.zfunc/_poetry
            autoload -Uz compinit && compinit
            ;;
        esac
    fi
}

function env_node_fnm_install() {
    mkdir -p "${FNM_DIR}/bin"
    curl -fsSL https://fnm.vercel.app/install | bash -s -- --install-dir "${FNM_DIR}/bin" --skip-shell
    fnm install --lts
}

function env_node_fnm_config() {
    if [[ $(command -v fnm) ]]; then
        eval "$(fnm env)"
        case "${ENV_SHELL}" in
        bash)
            eval "$(fnm completions --shell bash)"
            ;;
        zsh)
            eval "$(fnm completions --shell zsh)"
            ;;
        esac
    fi
}

function env_rust_install() {
    curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | bash -s -- \
        --no-modify-path \
        --default-toolchain=stable \
        --profile=complete
}

function env_rust_config() {
    if [[ $(command -v cargo) ]]; then
        case "${ENV_SHELL}" in
        bash)
            eval "$(rustup completions bash)"
            eval "$(rustup completions bash cargo)"
            ;;
        zsh)
            mkdir -p ~/.zfunc
            rustup completions zsh > ~/.zfunc/_rustup
            rustup completions zsh cargo > ~/.zfunc/_cargo
            autoload -Uz compinit && compinit
            ;;
        esac
        if [[ ! -f "${CARGO_HOME}/config.toml" ]]; then
            mkdir -p "${CARGO_HOME}"
            cat <<- "EOF" | tee "${CARGO_HOME}/config.toml" > /dev/null
[source.tuna]
registry = "https://mirrors.tuna.tsinghua.edu.cn/git/crates.io-index.git"
[source.crates-io]
replace-with = "tuna"
EOF
        fi
    fi
}

function env_go_g_install() {
    local version="$(curl -s "https://api.github.com/repos/voidint/g/releases/latest" | jq -r ".tag_name" | sed 's/v//')"
    local url="https://github.com/voidint/g/releases/download/v${version}/g${version}.${ENV_OS}-${ENV_ARCH}.tar.gz"
    wget "${url}" -O /opt/env/g.tar.gz
    mkdir -p "${G_HOME}/bin"
    tar -zxvf /opt/env/g.tar.gz -C "${G_HOME}/bin"
    g install "$(g ls-remote | grep -vE 'rc|beta' | tail -n 1)"
}

config_alias
env_homebrew_config
env_python_config
env_python_miniconda_config
env_python_poetry_config
env_node_fnm_config
env_rust_config
