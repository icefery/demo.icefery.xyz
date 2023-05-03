NAMESPACE=demo
RELEASE=elasticsearch
VERSION=19.6.0

function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm repo update
    helm upgrade ${RELEASE} bitnami/elasticsearch --install --namespace ${NAMESPACE} --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall ${RELEASE} --namespace ${NAMESPACE}
    kubectl delete pvc --all --namespace ${NAMESPACE}
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
