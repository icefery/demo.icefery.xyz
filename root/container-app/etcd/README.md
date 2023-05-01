# etcd

## 测试

```shell
kubectl run etcd-client \
    --restart="Never" \
    --image docker.io/bitnami/etcd:3.5.4-debian-11-r22 \
    --env ETCDCTL_ENDPOINTS=etcd.etcd.svc.cluster.local:2379 \
    --namespace etcd \
    --command -- sleep infinity

kubectl exec --namespace etcd -it etcd-client -- bash

etcdctl put /message Hello

etcdctl get /message
```
