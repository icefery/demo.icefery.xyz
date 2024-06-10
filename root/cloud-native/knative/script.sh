KNATIVE_HOME=/opt/env/knative

function get_latest_release_tag() {
    REPO="$1"
    TAG=$(curl -fsSL "https://api.github.com/repos/${REPO}/releases/latest" | jq -r ".tag_name")
    echo "$TAG"
}

function download_cli() {
    ARCH=$(dpkg --print-architecture)
    # kn
    TAG=v1.10.0
    wget -qO - "https://github.com/knative/client/releases/download/$TAG/kn-linux-$ARCH" | install "$KNATIVE_HOME/bin/kn"
    # kn-operator
    TAG=knative-v1.7.1
    wget -qO - "https://github.com/knative/client/releases/download/$TAG/kn-operator-linux-$ARCH" | install "$KNATIVE_HOME/bin/kn-operator"
    # kn-func
    TAG=v1.10.0
    wget -qO - "https://github.com/knative/func/releases/download/$TAG/func_linux_$ARCH}" | install "$KNATIVE_HOME/bin/kn-func"
    ln -snf "$KNATIVE_HOME/bin/kn-func" "$KNATIVE_HOME/bin/func"

    if [[ ! -f "/etc/profile.d/knative.sh" ]]; then
        tee /etc/profile.d/knative.sh <<- "EOF"
export KNATIVE_HOME=$KNATIVE_HOME
export PATH=\$KNATIVE_HOME/bin:\$PATH
source <(kn completion bash)
source <(func completion bash)
EOF
    fi
}

function install_knative() {
    # operator
    kn operator install --namespace knative-operator --version 1.7.1
    # serving
    kn operator install --component serving --namespace knative-serving --version 1.7
    kn operator enable ingress --istio -n knative-serving
    # eventing
    kn operator install --component eventing --namespace knative-eventing --version 1.7
}

LIST=(
    download_cli
    install_knative
)

if [[ -n $1 ]]; then
    $1
else
    select f in "${LIST[@]}"; do
        $f
    done
fi
