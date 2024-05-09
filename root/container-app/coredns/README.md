# CoreDNS

## 自定义 DNS 服务

#### Docker 安装 CoreDNS

```shell
mkdir -p /d/mount/coredns/

cp -f Corefile /d/mount/coredns/

nerdctl network create compose

nerdctl compose up -d
```

### Helm 安装 CoreDNS

```shell
helm repo add coredns https://coredns.github.io/helm

helm repo update

helm upgrade coredns coredns/coredns --install --namespace kube-system --create-namespace --values values.yaml --version 1.19.4
```

## 在 K8S 集群内引入自定义 DNS 服务

```shell
kubectl apply -f cm.yaml
```

## 测试

```shell
kubectl run -it --rm --restart=Never --image=infoblox/dnstools:latest dnstools
```

## TODO

-   直接使用 HelmChart 部署自定义 DNS 服务

## 常见问题

-   `systemd-resolved` 占用 53 端口

    ```shell
    sed -i -e '/#DNSStubListener=/c DNSStubListener=no' -e '/#DNS=/c DNS=114.114.114.114' /etc/systemd/resolved.conf

    ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf

    systemctl restart systemd-resolved
    ```
