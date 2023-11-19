NAMESPACE=cert-manager
VERSION=0.9.6

function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm repo update
    helm upgrade cert-manager bitnami/cert-manager --install --namespace ${NAMESPACE} --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall cert-manager --namespace ${NAMESPACE}
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
