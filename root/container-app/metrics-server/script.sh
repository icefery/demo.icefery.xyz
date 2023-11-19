NAMESPACE=kube-system
VERSION=6.2.17

function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm repo update
    helm upgrade metrics-server bitnami/metrics-server --install --namespace ${NAMESPACE} --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall metrics-server --namespace ${NAMESPACE}
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
