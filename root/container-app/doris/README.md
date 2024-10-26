# Doris

## Doris Operator

```shell
# 添加资源定义
kubectl create -f https://raw.githubusercontent.com/apache/doris-operator/$(curl -s https://api.github.com/repos/apache/doris-operator/releases/latest | grep tag_name | cut -d '"' -f4)/config/crd/bases/doris.selectdb.com_dorisclusters.yaml

# 部署 Doris Operator
kubectl apply -f https://raw.githubusercontent.com/apache/doris-operator/$(curl -s  https://api.github.com/repos/apache/doris-operator/releases/latest | grep tag_name | cut -d '"' -f4)/config/operator/operator.yaml

# 部署 Doris 集群
kubectl apply -f doris.yaml
```

## 设置密码

```sql
set password for 'root' = password('root')
```
