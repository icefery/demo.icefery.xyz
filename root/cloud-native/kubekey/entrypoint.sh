function kubernetes::install() {
    # install sysytem requirements
    apt install -y socat conntrack ebtables ipset ipvsadm
    # download kubekey
    export KKZONE=cn
    export VERSION=3.0.7
    curl -sfL https://get-kk.kubesphere.io | sh -
    # create kubernetes cluster
    ./kk create cluster -f config-sample.yaml
    # config bash completion
    crictl completion | sudo tee /etc/bash_completion.d/crictl > /dev/null
    kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null
    helm completion bash | sudo tee /etc/bash_completion.d/helm > /dev/null
    source /etc/profile
}

function kubernetes::uninstall() {
    ./kk delete cluster -f config-sample.yaml
    ./kk delete cluster
}

function kubesphere::install() {
    kubectl apply -f https://github.com/kubesphere/ks-installer/releases/download/v3.3.2/kubesphere-installer.yaml
    kubectl apply -f https://github.com/kubesphere/ks-installer/releases/download/v3.3.2/cluster-configuration.yaml
}

function kubesphere::uninstall() {
    curl https://raw.githubusercontent.com/kubesphere/ks-installer/release-3.3/deploy/cluster-configuration.yaml | bash
}

$1
