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
}

function uninstall() {
    /usr/local/bin/k3s-uninstall.sh
    /usr/local/bin/k3s-agent-uninstall.sh
}

function init() {
    CONTAINER_APP=../../container-app
    # coredns
    cd $CONTAINER_APP/coredns
    bash script.sh helm::install
    # metallb
    cd $CONTAINER_APP/metallb
    bash script.sh helm::install
    # metrcis-server
    cd $CONTAINER_APP/metrics-server
    bash script.sh helm::install
    # traefik
    cd $CONTAINER_APP/traefik
    bash script.sh helm::install
    # longhorn
    cd $CONTAINER_APP/longhorn
    bash script.sh helm::install
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
