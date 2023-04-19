function helm::install() {
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm upgrade metrics-server bitnami/metrics-server --install --namespace metrics-server --create-namespace --values values.yaml --version 6.2.17
}

function helm::uninstall() {
    helm --namespace metrics-server uninstall metrics-server
}

$1
