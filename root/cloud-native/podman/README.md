# Podman

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
