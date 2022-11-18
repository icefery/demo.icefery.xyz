> 默认端口范围是 `30000-32767`

```bash
vim /etc/kubernetes/manifests/kube-apiserver.yaml
```

```bash
--service-node-port-range=1-65535
```
