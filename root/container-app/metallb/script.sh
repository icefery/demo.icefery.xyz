function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm repo update
    helm upgrade metallb bitnami/metallb --install --namespace metallb-system --create-namespace
    # IPVS strictARP
    # kubectl get configmap kube-proxy -n kube-system -o yaml | sed -e "s/strictARP: false/strictARP: true/" | kubectl diff -f - -n kube-system
    kubectl apply -f config.yaml -n metallb-system
}

function helm::uninstall() {
    helm uninstall metallb --namespace metallb-system
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
