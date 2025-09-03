# Podman

## 安装

```shell
sudo pacman -S podman podman-compose

sudo mkdir -p /etc/containers

sudo tee /etc/containers/registries.conf > /dev/null <<- "EOF"
unqualified-search-registries = ["docker.io"]
EOF

sudo tee /etc/containers/policy.json > /dev/null <<- "EOF"
{
  "default": [
    { "type": "insecureAcceptAnything" }
  ]
}
EOF
```

## 卸载

```shell
sudo rm -rf /etc/containers
sudo rm -rf /var/lib/containers
sudo rm -rf /var/run/containers
sudo rm -rf ~/.local/share/containers
```

## 配置镜像源

```shell
sudo vim /etc/containers/registries.conf
```

```toml
unqualified-search-registries = ["docker.io"]

# [[registry]]
# prefix = "docker.io"
# insecure = false
# location = "docker.1panel.live"

[[registry]]
prefix = "docker.io"
insecure = true
location = "192.168.31.101:50080"

[[registry]]
prefix = "harbor.example.org"
insecure = true
location = "harbor.example.org"
```

## 收藏

#### rootless 容器自动退出

-   https://github.com/containers/podman/blob/main/troubleshooting.md#17-rootless-containers-exit-once-the-user-session-exits
-   https://github.com/containers/podman/blob/main/troubleshooting.md#21-a-rootless-container-running-in-detached-mode-is-closed-at-logout

```shell
sudo loginctl enable-linger <UID>
```
