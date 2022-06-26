# CoreDNS

## 常见问题

### 解决 `systemd-resolved` 占用 53 端口问题

```bash
sed -i -e '/#DNSStubListener=/c DNSStubListener=no' -e '/#DNS=/c DNS=127.0.0.1' /etc/systemd/resolved.conf

ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf

systemdctl restart systemd-resolved
```
