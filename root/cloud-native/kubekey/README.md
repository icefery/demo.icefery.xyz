# KubeKey

## 快速开始

```shell
apt install socat conntrack ebtables ipset ipvsadm -y

export KKZONE=cn

curl -sfL https://get-kk.kubesphere.io | sh -

./kk create config

vim config-sample.yaml

./kk create cluster -f config-sample.yaml
```

```yaml
apiVersion: kubekey.kubesphere.io/v1alpha2
kind: Cluster
metadata:
  name: sample
spec:
  hosts:
    - { name: k8s-211, address: 192.192.192.211, internalAddress: 192.192.192.211, user: root, password: root }
    - { name: k8s-212, address: 192.192.192.212, internalAddress: 192.192.192.212, user: root, password: root }
    - { name: k8s-213, address: 192.192.192.213, internalAddress: 192.192.192.213, user: root, password: root }
  roleGroups:
    etcd:
      - k8s-[211:213]
    control-plane:
      - k8s-[211:213]
    worker:
      - k8s-[211:213]
  controlPlaneEndpoint:
    internalLoadbalancer: haproxy
    domain: lb.kubesphere.local
    address: ''
    port: 6443
  kubernetes:
    version: v1.24.7
    clusterName: cluster.local
    autoRenewCerts: true
    containerManager: containerd
  etcd:
    type: kubekey
  network:
    plugin: calico
    kubePodsCIDR: 10.233.64.0/18
    kubeServiceCIDR: 10.233.0.0/18
    multusCNI:
      enabled: false
  registry:
    privateRegistry: ''
    namespaceOverride: ''
    registryMirrors: ['https://uwk49ut2.mirror.aliyuncs.com', 'https://registry.docker-cn.com']
    insecureRegistries: []
  addons: []
```
