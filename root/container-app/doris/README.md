# Doris

## Doris Operator

```shell
# 添加资源定义
kubectl create -f "https://raw.githubusercontent.com/apache/doris-operator/master/config/crd/bases/doris.apache.com_dorisclusters.yaml"

# 部署 Doris Operator
kubectl apply -f "https://raw.githubusercontent.com/apache/doris-operator/master/config/operator/operator.yaml"

# 部署 Doris 集群
kubectl apply -f doris.yaml
```

## 设置密码

```sql
set password for 'root' = password('root')
```
