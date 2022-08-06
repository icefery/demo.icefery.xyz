# CoreDNS

## 自定义 DNS 服务

```bash
mkdir -p /d/mount/coredns/

cp -f Corefile /d/mount/coredns/

nerdctl network create dev

nerdctl compose up -d
```

## 在 K8S 集群内引入自定义 DNS 服务

```bash
kubectl apply -f cm.yaml
```

## 测试

```bash
kubectl run -it --rm --restart=Never --image=infoblox/dnstools:latest dnstools
```

## TODO

- 直接使用 HelmChart 部署自定义 DNS 服务

## 常见问题

- `systemd-resolved` 占用 53 端口

  ```bash
  sed -i -e '/#DNSStubListener=/c DNSStubListener=no' -e '/#DNS=/c DNS=114.114.114.114' /etc/systemd/resolved.conf

  ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf

  systemctl restart systemd-resolved
  ```
