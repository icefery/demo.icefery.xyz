function helm_install() {
    helm repo add coredns https://coredns.github.io/helm
    helm repo update
    helm install coredns coredns/coredns --namespace kube-system --create-namespace --values values.yaml
}

function helm_uninstall() {
    helm uninstall coredns --namespace kube-system
}

LIST=(
    helm_install
    helm_uninstall
)

if [[ -n $1 ]]; then
    $1
else
    select f in "${LIST[@]}"; do
        $f
    done
fi
