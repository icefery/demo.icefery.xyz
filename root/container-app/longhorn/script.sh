NAMESPACE=longhorn-system
VERSION=1.4.1

function helm::install() {
    helm repo add longhorn https://charts.longhorn.io
    helm repo update
    helm install longhorn longhorn/longhorn --namespace longhorn-system --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall longhorn -n longhorn-system
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
