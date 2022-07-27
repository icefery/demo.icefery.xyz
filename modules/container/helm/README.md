# Helm

## 安装

```bash
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash

helm completion bash > /etc/bash_completion.d/helm

source /etc/profile
```

## 收藏

#### 资源执行顺序

> https://github.com/helm/helm/blob/release-3.0/pkg/releaseutil/kind_sorter.go

```go
package releaseutil

type KindSortOrder []string

var InstallOrder KindSortOrder = []string{
    "Namespace",
    "NetworkPolicy",
    "ResourceQuota",
    "LimitRange",
    "PodSecurityPolicy",
    "PodDisruptionBudget",
    "Secret",
    "ConfigMap",
    "StorageClass",
    "PersistentVolume",
    "PersistentVolumeClaim",
    "ServiceAccount",
    "CustomResourceDefinition",
    "ClusterRole",
    "ClusterRoleList",
    "ClusterRoleBinding",
    "ClusterRoleBindingList",
    "Role",
    "RoleList",
    "RoleBinding",
    "RoleBindingList",
    "Service",
    "DaemonSet",
    "Pod",
    "ReplicationController",
    "ReplicaSet",
    "Deployment",
    "HorizontalPodAutoscaler",
    "StatefulSet",
    "Job",
    "CronJob",
    "Ingress",
    "APIService",
}

var UninstallOrder KindSortOrder = []string{
    "APIService",
    "Ingress",
    "Service",
    "CronJob",
    "Job",
    "StatefulSet",
    "HorizontalPodAutoscaler",
    "Deployment",
    "ReplicaSet",
    "ReplicationController",
    "Pod",
    "DaemonSet",
    "RoleBindingList",
    "RoleBinding",
    "RoleList",
    "Role",
    "ClusterRoleBindingList",
    "ClusterRoleBinding",
    "ClusterRoleList",
    "ClusterRole",
    "CustomResourceDefinition",
    "ServiceAccount",
    "PersistentVolumeClaim",
    "PersistentVolume",
    "StorageClass",
    "ConfigMap",
    "Secret",
    "PodDisruptionBudget",
    "PodSecurityPolicy",
    "LimitRange",
    "ResourceQuota",
    "NetworkPolicy",
    "Namespace",
}
```
