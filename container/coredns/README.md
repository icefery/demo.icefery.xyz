# CoreDNS

## 配置 `systemd-resolved`

```bash
sed -i -e '/#DNSStubListener=/c DNSStubListener=no' -e '/#DNS=/c DNS=127.0.0.1' /etc/systemd/resolved.conf

ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf

systemdctl restart systemd-resolved
```
