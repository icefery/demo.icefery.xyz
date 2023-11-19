NAMESPACE=kube-system
VERSION=1.22.0

function helm::install() {
    helm repo add coredns https://coredns.github.io/helm
    helm repo update
    helm install coredns coredns/coredns --namespace ${NAMESPACE} --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall coredns --namespace ${NAMESPACE}
}

LIST=(
    helm::install
    helm::uninstall
)

if [[ -n $1 ]]; then
    $1
else
    select f in "${LIST[@]}"; do
        $f
    done
fi
