NAMESPACE=traefik
VERSION=10.24.0

function helm::install() {
    helm repo add traefik https://helm.traefik.io/traefik
    helm repo update
    helm upgrade traefik traefik/traefik --install --namespace ${NAMESPACE} --create-namespace --values values.yaml
}

function helm::uninstall() {
    helm uninstall traefik --namespace ${NAMESPACE}
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
