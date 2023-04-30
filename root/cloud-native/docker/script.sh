function install() {
    curl -fsSL https://get.docker.com | sudo bash
    sudo sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/apt/sources.list.d/docker.list
    echo '{ "registry-mirrors": ["https://hub-mirror.c.163.com", "https://uwk49ut2.mirror.aliyuncs.com"] }' | sudo tee /etc/docker/daemon.json > /dev/null
    sudo systemctl daemon-reload
    sudo systemctl disable docker.socket
    sudo systemctl restart docker.service
}

function uninstall() {
    sudo apt purge docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin docker-ce-rootless-extras
    sudo rm -rf /var/lib/docker
    sudo rm -rf /var/lib/containerd
}

LIST=(
    install
    uninstall
)

if [[ -n "$1" ]]; then
    $1
else
    select f in ${LIST[@]}; do
        $f
    done
fi
