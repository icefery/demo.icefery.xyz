# CoreDNS

## Install

```bash
sed -i '/DNSStubListener/c DNSStubListener=no' /etc/systemd/resolved.conf

ln -sf /run/systemd/resolve/resolv.conf /etc/resolv.conf

systemdctl restart systemd-resolved
```
