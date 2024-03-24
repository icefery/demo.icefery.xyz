> 默认端口范围是 `30000-32767`

```shell
vim /etc/kubernetes/manifests/kube-apiserver.yaml
```

```shell
--service-node-port-range=1-65535
```
