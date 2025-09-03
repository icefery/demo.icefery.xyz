---
title: 'NFS 动态 PVC 的 StorageClass 和 StatefulSet'
---

## 参考资料

-   [K3S/K8S 中动态创建 PVC 时 SelfLink 问题解决](https://zhuanlan.zhihu.com/p/468467734)

## 搭建 NFS 服务器

1. 安装

    ```shell
    apt install -y nfs-kernel-server
    ```

2. 创建共享目录

    ```shell
    mkdir -p /d/nfs
    ```

3. 导出

    ```shell
    echo '/d/nfs *(rw,sync,no_root_squash)' >> /etc/exports

    exportfs -r
    ```

## 创建 NFS StorageClass

1. 克隆 NFS Provisioner 项目

    ```shell
    git clone https://github.com/kubernetes-sigs/nfs-subdir-external-provisioner.git
    ```

2. 下载资源文件

    ```shell
    wget -O nfs-client-provisioner-rbac.yaml   https://raw.githubusercontent.com/kubernetes-sigs/nfs-subdir-external-provisioner/master/deploy/rbac.yaml

    wget -O nfs-client-provisioner-deploy.yaml https://raw.githubusercontent.com/kubernetes-sigs/nfs-subdir-external-provisioner/master/deploy/deployment.yaml

    wget -O nfs-client-sc.yaml                 https://raw.githubusercontent.com/kubernetes-sigs/nfs-subdir-external-provisioner/master/deploy/class.yaml
    ```

3. 批量替换 Namespace

    ```shell
    NAMESPACE=local

    sed -i'' "s/namespace:.*/namespace: $NAMESPACE/g" nfs-client-provisioner-deploy.yaml nfs-client-provisioner-rbac.yaml
    ```

4. 修改 Deployment 配置

    - 镜像 `registry.cn-beijing.aliyuncs.com/pylixm/nfs-subdir-external-provisioner:v4.0.0`
    - NFS 主机 `192.192.192.6`
    - NFS 目录 `/d/nfs`

5. 创建资源

    ```shell
    kubectl create -f nfs-client-provisioner-rbac.yaml

    kubectl create -f nfs-client-provisioner-deploy.yaml

    kubectl create -f nfs-client-sc.yaml
    ```

## 创建 StatefulSet

-   `mysql-sts.yaml`

    ```yaml
    apiVersion: apps/v1
    kind: StatefulSet
    metadata:
      namespace: local
      name: mysql-sts
      labels:
        app: mysql
    spec:
      selector:
        matchLabels:
          app: mysql
      serviceName: mysql
      replicas: 1
      template:
        metadata:
          labels:
            app: mysql
        spec:
          containers:
            - name: mysql
              image: mysql:8.0.20
              args:
                - --lower-case-table-names=1
                - --default-authentication-plugin=mysql_native_password
                - --default-time-zone=+8:00
              env:
                - name: MYSQL_ROOT_PASSWORD
                  value: root
              ports:
                - containerPort: 3306
              volumeMounts:
                - mountPath: /var/lib/mysql
                  name: data
      volumeClaimTemplates:
        - metadata:
            name: data
          spec:
            storageClassName: nfs-client
            accessModes:
              - ReadWriteOnce
            resources:
              requests:
                storage: 1Gi
    ```

-   `mysql-svc.yaml`

    ```yaml
    apiVersion: v1
    kind: Service
    metadata:
      namespace: local
      name: mysql-svc
      labels:
        app: mysql
    spec:
      type: ClusterIP
      clusterIP: None
      selector:
        app: mysql
      ports:
        - port: 3306
    ```

## 检查

```shell
kubectl get all -n local -o wide

kubectl get pvc -n local -o wide

kubectl get pv -o wide
```
