function install() {
    curl -fsSL https://get.docker.com | sudo bash
    sudo sed -i 's download.docker.com mirrors.tuna.tsinghua.edu.cn/docker-ce g' /etc/apt/sources.list.d/docker.list
    sudo tee /etc/docker/daemon.json > /dev/null <<- "EOF"
{
  "data-root": "/opt/data/docker",
  "registry-mirrors": ["https://uwk49ut2.mirror.aliyuncs.com"]
}
EOF
    sudo systemctl daemon-reload
    sudo systemctl restart docker

    docker run --privileged --rm tonistiigi/binfmt --install all
    docker buildx create --name=my-builder --bootstrap --use
}

function uninstall() {
    # https://docs.docker.com/engine/install/ubuntu/#uninstall-old-versions
    for pkg in docker.io docker-doc docker-compose docker-compose-v2 podman-docker containerd runc; do sudo apt-get remove $pkg; done
    # https://docs.docker.com/engine/install/ubuntu/#uninstall-docker-engine
    sudo apt-get purge docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin docker-ce-rootless-extras
    sudo rm -rf /var/lib/docker
    sudo rm -rf /var/lib/containerd
}

case $1 in
install)
    install
    ;;
uninstall)
    uninstall
    ;;
*)
    echo "Usage: $0 <install|uninstall>"
    ;;
esac
