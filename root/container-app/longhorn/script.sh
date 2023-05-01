NAMESPACE=longhorn-system
VERSION=1.4.1

function helm::install() {
    # https://artifacthub.io/packages/helm/longhorn/longhorn#installation
    helm repo add longhorn https://charts.longhorn.io
    helm repo update
    helm install longhorn longhorn/longhorn --namespace longhorn-system --values values.yml
}

function helm::uninstall() {
    # https://artifacthub.io/packages/helm/longhorn/longhorn#uninstallation
    kubectl -n longhorn-system patch -p '{"value": "true"}' --type=merge lhs deleting-confirmation-flag
    helm uninstall longhorn -n longhorn-system
    kubectl delete namespace longhorn-system
}

LIST=(
    helm::install
    helm::uninstall
)

if [[ -n "$1" ]]; then
    $1
else
    select f in ${LIST[@]}; do
        $f
    done
fi
