# Kubernetes

## 常见问题

#### 0/1 nodes are available: 1 node(s) had taint {node-role.kubernetes.io/master: }, that the pod didn't tolerate.

```shell
kubectl taint nodes --all node-role.kubernetes.io/master-
```

## 常用命令

#### 清理 `Completed` 状态的 Pod

```shell
kubectl get pod --field-selector=status.phase==Succeeded


kubectl delete pod --field-selector=status.phase==Succeeded
```

#### 删除不用的 ReplicaSet

```shell
NAMESPACE=test
kubectl get replicaset.apps -A | grep "0         0         0" | awk '{print $2}' | xargs kubectl delete replicaset.apps -n ${NAMESPACE}
```

#### 无法删除命名空间

```shell
# 代理接口
kubectl proxy --port=8081

NS=longhorn-system
# 导出资源文件
kubectl get namespaces ${NS} -o json > ${NS}.json
# 调用接口
curl -k -H "Content-Type:application/json" -X PUT --data-binary @${NS}.json http://127.0.0.1:8081/api/v1/namespaces/${NS}/finalize
```

#### 查看某个命名空间下的所有镜像

```shell
kubectl get pods -n harbor -o=jsonpath='{range .items[*]}{range .spec.containers[*]}{.image}{"\n"}{end}{end}'
```

## 收藏

#### [为 pod 配置服务账户(Service Account)](https://www.coderdocument.com/docs/kubernetes/v1.14/tasks/configure_pods_and_containers/configure_service_accounts_for_pods.html)
