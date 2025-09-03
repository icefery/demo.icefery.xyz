# etcd

## 测试

```shell
kubectl run etcd-client --restart=Never --image docker.io/bitnami/etcd:3.5 --env ETCDCTL_ENDPOINTS=etcd.etcd.svc.cluster.local:2379 --namespace etcd --command -- sleep infinity
kubectl exec --namespace etcd -it etcd-client -- bash

etcdctl put /skydns/xyz/icefery/demo/x1 '{ "host": "192.168.8.110", "ttl": 60 }'
etcdctl get --prefix /skydns
```
