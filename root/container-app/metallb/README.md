# MetalLB

## Layer2 模式

#### IPVS 模式下启用 strictARP

> [INSTALLATION](https://metallb.universe.tf/installation/)

```shell
kubectl -n kube-system edit configmaps kube-proxy
```

```yaml
mode: ipvs
ipvs:
  strictARP: true
```
