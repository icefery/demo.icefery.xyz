export ZSH="$HOME/.oh-my-zsh"

ZSH_THEME="ys"

plugins=(sudo)

source $ZSH/oh-my-zsh.sh

alias ll="ls -lh -A -G -D '%Y-%m-%d %H:%M:%S'"

function start_proxy() {
    export http_proxy=http://127.0.0.1:7890
    export https_proxy=$http_proxy
}

function stop_proxy() {
    unset http_proxy
    unset https_proxy
}

function config_miniconda() {
    [[ $(command -v conda) ]] && eval "$(conda shell.zsh hook 2> /dev/null)"
}

function config_fnm() {
    if [[ $(command -v fnm) ]]; then
        eval "$(fnm env)"
        eval "$(fnm completions --shell zsh)"
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

config_miniconda
config_fnm
config_cargo
config_pip
