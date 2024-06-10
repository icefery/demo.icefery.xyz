function install() {
    mkdir -p /etc/rancher/k3s

    if [[ ! -f "/etc/rancher/k3s/registries.yaml" ]]; then
        tee /etc/rancher/k3s/registries.yaml > /dev/null <<- "EOF"
mirrors:
  docker.io:
    endpoint:
      - https://docker.mirrors.sjtug.sjtu.edu.cn
      - https://docker.nju.edu.cn
      - https://uwk49ut2.mirror.aliyuncs.com
configs:
  ghcr.io:
    auth:
      username:
      password:

EOF
    fi

    if [[ ! -f "/etc/profile.d/k3s.sh" ]]; then
        tee /etc/profile.d/k3s.sh > /dev/null <<- "EOF"
export KUBECONFIG="/etc/rancher/k3s/k3s.yaml"
[[ -x /usr/local/bin/k3s ]]     && eval "$(k3s     completion bash)"
[[ -x /usr/local/bin/kubectl ]] && eval "$(kubectl completion bash)"
[[ -x /usr/local/bin/helm ]]    && eval "$(helm    completion bash)"
EOF
    fi

    if [[ ! -x "/usr/local/bin/k3s" ]]; then
        curl -fsSL https://rancher-mirror.rancher.cn/k3s/k3s-install.sh | sudo INSTALL_K3S_MIRROR="cn" INSTALL_K3S_VERSION="v1.27.13+k3s1" sh -s - server \
            --data-dir="/data/k3s/var/lib/rancher/k3s" \
            --write-kubeconfig-mode="644" \
            --cluster-cidr="10.8.0.0/16" \
            --service-cidr="10.16.0.0/16" \
            --cluster-dns="10.16.0.10" \
            --service-node-port-range="1-65535" \
            --kube-proxy-arg="proxy-mode=ipvs" \
            --disable-cloud-controller \
            --disable="coredns" \
            --disable="servicelb" \
            --disable="traefik" \
            --disable="local-storage" \
            --disable="metrics-server" \
            --datastore-endpoint="http://192.168.31.101:2379"
        #   --datastore-endpoint="mysql://k3s:k3s@tcp(192.168.31.101:3306)/k3s"
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

case $1 in
install)
    install
    ;;
uninstall)
    uninstall
    ;;
*)
    echo "USAGE: $1 <install|uninstall>"
    ;;
esac
