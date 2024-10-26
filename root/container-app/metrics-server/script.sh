function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm repo update
    helm upgrade metrics-server bitnami/metrics-server --install --namespace kube-system --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall metrics-server --namespace kube-system
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
