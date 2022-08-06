# etcd

## 安装

```bash
kubectl -n etcd delete pvc --all
```

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade etcd bitnami/etcd --install --namespace etcd --create-namespace --values values.yaml --version 8.3.7
```

## TODO

- 通过 Ingress 的方式暴露 etcd 服务

## 测试

```bash
kubectl run etcd-client --restart="Never" --image docker.io/bitnami/etcd:3.5.4-debian-11-r22 --env ETCDCTL_ENDPOINTS="etcd.etcd.svc.cluster.local:2379" --namespace etcd --command -- sleep infinity

kubectl exec --namespace etcd -it etcd-client -- bash

etcdctl put /message Hello

etcdctl get /message
```
