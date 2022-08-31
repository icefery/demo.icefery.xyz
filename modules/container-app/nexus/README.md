# Nexus

## Helm 安装 Nexus

```bash
helm repo add sonatype https://sonatype.github.io/helm3-charts/

helm repo update

helm upgrade nexus sonatype/nexus-repository-manager --install --namespace nexus --create-namespace --values values.yaml --version 41.1.2

# 查看默认密码
kubectl exec -n nexus pods/<POD> -- cat /nexus-data/admin.password
```

## 注意事项

#### Nexus 并不能识别 Ingress Controller 暴露的非 80 和 443 以外的端口
