NAMESPACE=kube-system
RELEASE=etcd
VERSION=8.10.1

function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm repo update
    helm upgrade ${RELEASE} bitnami/etcd --install --namespace ${NAMESPACE} --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall ${RELEASE} --namespace ${NAMESPACE}
    kubectl delete pvc --all --namespace ${NAMESPACE}
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
