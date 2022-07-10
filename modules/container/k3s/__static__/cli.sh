#!/usr/bin/env bash

set -e

function uninstall_k3s() {
    set +e
    /usr/local/bin/k3s-uninstall.sh
    /usr/local/bin/k3s-agent-uninstall.sh
    set -e
}

function install_k3s() {
    curl -sfL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn sh -s - --service-node-port-range 1-65535 --disable traefik --disable metrics-server
    cat registries.yaml > /etc/rancher/k3s/registries.yam
    systemctl restart k3s

    echo "export KUBECONFIG=/etc/rancher/k3s/k3s.yaml" >> /etc/bash.bashrc
    kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null
    source /etc/profile
}

function install_helm() {
    curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
    helm completion bash > /etc/bash_completion.d/helm
    source /etc/profile
}

$1
