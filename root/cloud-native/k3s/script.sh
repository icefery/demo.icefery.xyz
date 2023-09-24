function install() {
    mkdir -p /etc/rancher/k3s

    if [[ ! -f "/etc/rancher/k3s/registries.yam" ]]; then
        tee /etc/rancher/k3s/registries.yaml > /dev/null <<- 'EOF'
mirrors:
  docker.io:
    endpoint:
      - https://hub-mirror.c.163.com
      - https://uwk49ut2.mirror.aliyuncs.com
configs:
  ghcr.io:
    auth:
      username:
      password:
EOF
    fi

    if [[ ! -f "/etc/profile.d/k3s.sh" ]]; then
        tee /etc/profile.d/k3s.sh > /dev/null <<- 'EOF'
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
eval "$(k3s completion bash)"
eval "$(kubectl completion bash)"
EOF
    fi

    if [[ ! -x "/usr/local/bin/k3s" ]]; then
        curl -fsSL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_VERSION=v1.23.17+k3s1 bash -s - server \
            --data-dir /data/k3s/var/lib/rancher/k3s \
            --cluster-cidr 10.8.0.0/16 \
            --service-cidr 10.16.0.0/16 \
            --cluster-dns 10.16.0.10 \
            --service-node-port-range 1-65535 \
            --kube-proxy-arg proxy-mode=ipvs \
            --disable coredns \
            --disable servicelb \
            --disable traefik \
            --disable local-storage \
            --disable metrics-server
    fi

    if [[ ! -x "/usr/local/bin/helm" ]]; then
        curl -fsSL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
    fi

    source /etc/profile
}

function uninstall() {
    if [[ -x "/usr/local/bin/k3s-uninstall.sh" ]]; then
        /usr/local/bin/k3s-uninstall.sh
    fi

    if [[ -x "/usr/local/bin/k3s-agent-uninstall.sh" ]]; then
        /usr/local/bin/k3s-agent-uninstall.sh
    fi

    rm -rf /var/lib/rancher
    rm -rf /data/k3s/var/lib/rancher
}

LIST=(
    install
    uninstall
)

if [[ -n $1 ]]; then
    $1
else
    select f in "${LIST[@]}"; do
        $f
    done
fi
