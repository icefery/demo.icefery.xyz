NAMESPACE=longhorn-system
VERSION=1.4.1

function helm::install() {
    helm repo add longhorn https://charts.longhorn.io
    helm repo update
    helm upgrade longhorn longhorn/longhorn --install --namespace ${NAMESPACE} --create-namespace --values values.yml
}

function helm::uninstall() {
    helm uninstall longhorn --namespace ${NAMESPACE}
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
