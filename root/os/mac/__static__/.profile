export TZ="Asia/Shanghai"
export LANG="en_US.UTF-8"
export TIME_STYLE="+%Y-%m-%d %H:%M:%S"

# homebrew
export HOMEBREW_INSTALL_FROM_API=1
export HOMEBREW_API_DOMAIN="https://mirrors.tuna.tsinghua.edu.cn/homebrew-bottles/api"
export HOMEBREW_BOTTLE_DOMAIN="https://mirrors.tuna.tsinghua.edu.cn/homebrew-bottles"
export HOMEBREW_BREW_GIT_REMOTE="https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/brew.git"
export HOMEBREW_CORE_GIT_REMOTE="https://mirrors.tuna.tsinghua.edu.cn/git/homebrew/homebrew-core.git"
export HOMEBREW_PIP_INDEX_URL="https://pypi.tuna.tsinghua.edu.cn/simple"

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
