export KKZONE=cn

function kubekey::install() {
    if [[ -f "kk" ]]; then
        sudo apt install -y socat conntrack ebtables ipset ipvsadm
        curl -fsSL https://get-kk.kubesphere.io | VERSION=v3.0.7 sudo bash
    fi
}

function kubernetes::install() {
    ./kk create cluster -f config-sample.yaml
    crictl completion | sudo tee /etc/bash_completion.d/crictl > /dev/null
    kubectl completion bash | sudo tee /etc/bash_completion.d/kubectl > /dev/null
    helm completion bash | sudo tee /etc/bash_completion.d/helm > /dev/null
}

function kubernetes::uninstall() {
    ./kk delete cluster -f config-sample.yaml
    ./kk delete cluster
}

function kubesphere::install() {
    VERSION=V3.3.2
    kubectl apply -f https://github.com/kubesphere/ks-installer/releases/download/${VERSION}/kubesphere-installer.yaml
    kubectl apply -f https://github.com/kubesphere/ks-installer/releases/download/${VERSION}/cluster-configuration.yaml
}

function kubesphere::uninstall() {
    curl https://raw.githubusercontent.com/kubesphere/ks-installer/release-3.3/deploy/cluster-configuration.yaml | bash
}

LIST=(
    kubekey::install
    kubernetes::install
    kubernetes::uninstall
    kubesphere::install
    kubesphere::uninstall
)

if [[ -n $1 ]]; then
    $1
else
    select f in "${LIST[@]}"; do
        $f
    done
fi
