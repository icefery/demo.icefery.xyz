function install() {
    if [[ ! -d "/etc/rancher/k3s" ]]; then
        mkdir -p /etc/rancher/k3s
        tee /etc/profile.d/k3s.sh > /dev/null <<- 'EOF'
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
EOF
        tee /etc/rancher/k3s/registries.yaml > /dev/null <<- 'EOF'
mirrors:
  docker.io:
    endpoint:
      - https://hub-mirror.c.163.com
      - https://uwk49ut2.mirror.aliyuncs.com
EOF
    fi

    if [[ ! -x "/usr/local/bin/k3s" ]]; then
        export INSTALL_K3S_MIRROR=cn
        export INSTALL_K3S_VERSION=v1.24.13+k3s1
        # 可禁用的组件有 coredns | servicelb | traefik | local-storage | metrics-server
        curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | bash -s - \
            --disable coredns \
            --disable servicelb \
            --disable traefik \
            --disable local-storage \
            --disable metrics-server \
            --cluster-cidr 10.8.0.0/16 \
            --service-cidr 10.16.0.0/16 \
            --service-node-port-range 1-65535 \
            --cluster-dns 10.16.0.10
        k3s completion bash | tee /etc/bash_completion.d/k3s > /dev/null
        kubectl completion bash | tee /etc/bash_completion.d/kubectl > /dev/null
        crictl completion bash | tee /etc/bash_completion.d/crictl > /dev/null
    fi

    if [[ ! -x "/usr/local/bin/helm" ]]; then
        curl -fsSL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
        helm completion bash | tee /etc/bash_completion.d/helm > /dev/null
    fi

    source /etc/profile
}

function uninstall() {
    /usr/local/bin/k3s-uninstall.sh
    /usr/local/bin/k3s-agent-uninstall.sh
}

function init() {
    CONTAINER_APP_PATH=$(realpath -e -s ../../container-app)
    CONTAINER_APP_LIST=(
        coredns
        metallb
        metrics-server
        traefik
        longhorn
        etcd
        external-dns
    )
    for app in ${CONTAINER_APP_LIST[@]}; do
        cd ${CONTAINER_APP_PATH}/${app}
        if [[ $(helm list --no-headers -A | grep -c ${app}) == 0 ]]; then
            bash script.sh helm::install
        fi
    done
}

LIST=(
    install
    uninstall
    init
)

if [[ -n "$1" ]]; then
    $1
else
    select f in ${LIST[@]}; do
        $f
    done
fi
