## Native Kubernetes

### RBAC

```shell
kubectl create namespace flink
kubectl --namespace flink create serviceaccount flink
kubectl create clusterrolebinding flink-role --clusterrole=edit --serviceaccount=flink:flink
```

#### Session 模式

```shell
# 启动集群
bin/kubernetes-session.sh \
    -Dkubernetes.cluster-id=flink-session-cluster \
    -Dkubernetes.namespace=flink \
    -Dkubernetes.service-account=flink

# 提交任务
bin/flink run \
    --target kubernetes-session \
    -Dkubernetes.cluster-id=flink-session-cluster \
    -Dkubernetes.namespace=flink \
    -Dkubernetes.service-account=flink \
    examples/streaming/TopSpeedWindowing.jar

# 删除集群
kubectl --namespace flink delete deployment/flink-session-cluster
```

#### Application 模式

```shell
# 提交任务
FLINK_VERSION="1.16.0"
bin/flink run-application \
    --target kubernetes-application \
    -Dkubernetes.cluster-id=flink-application-cluster \
    -Dkubernetes.namespace=flink \
    -Dkubernetes.service-account=flink \
    -Dkubernetes.container.image.ref=flink:${FLINK_VERSION}-scala_2.12 \
    local:////opt/flink/examples/streaming/TopSpeedWindowing.jar

### 列出任务
bin/flink list \
    --target kubernetes-application \
    -Dkubernetes.cluster-id=flink-application-cluster \
    -Dkubernetes.namespace=flink \
    -Dkubernetes.service-account=flink

### 取消任务
bin/flink cancel \
    --target kubernetes-application \
    -Dkubernetes.cluster-id=flink-application-cluster \
    -Dkubernetes.namespace=flink \
    -Dkubernetes.service-account=flink \
    <JOB_ID>
```
