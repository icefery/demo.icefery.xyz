# CoreDNS

## K8S 集群 DNS

```bash
helm repo add coredns https://coredns.github.io/helm

helm upgrade coredns coredns/coredns \
  --install \
  --namespace=kube-system \
  --version 1.19.4
```

## 自定义 DNS 服务

```bash
nerdctl network create dev

nerdctl compose up -d
```

## 常见问题

- `systemd-resolved` 占用 53 端口

  ```bash
  sed -i -e '/#DNSStubListener=/c DNSStubListener=no' -e '/#DNS=/c DNS=127.0.0.1' /etc/systemd/resolved.conf
  
  ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf
  
  systemctl restart systemd-resolved
  ```
