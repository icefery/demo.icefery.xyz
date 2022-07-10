function install() {
    curl -fsSL https://get.docker.com -o get-docker.sh
    sudo sh get-docker.sh
}

function uninstall() {
    sudo apt-get purge docker-ce docker-ce-cli containerd.io docker-compose-plugin
    sudo rm -rf /var/lib/docker
    sudo rm -rf /var/lib/containerd
}
