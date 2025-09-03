# Nexus

## Helm 安装 Nexus

```shell
helm repo add sonatype https://sonatype.github.io/helm3-charts/

helm repo update

helm upgrade nexus sonatype/nexus-repository-manager --install --namespace nexus --create-namespace --values values.yaml --version 41.1.2

# 查看默认密码
kubectl exec -n nexus pods/<POD> -- cat /nexus-data/admin.password
```

## 仓库配置

#### Maven

<!-- prettier-ignore-start -->

```yaml
- { type: proxy,  name: maven-proxy-aliyun-public,        url: https://maven.aliyun.com/repository/public }
- { type: proxy,  name: maven-proxy-aliyun-google,        url: https://maven.aliyun.com/repository/google }
- { type: proxy,  name: maven-proxy-aliyun-spring,        url: https://maven.aliyun.com/repository/spring }
- { type: proxy,  name: maven-proxy-aliyun-spring-plugin, url: https://maven.aliyun.com/repository/spring-plugin }
- { type: proxy,  name: maven-proxy-aliyun-gradle-plugin, url: https://maven.aliyun.com/repository/gradle-plugin }
- { type: proxy,  name: maven-proxy-repo,                 url: https://repo.maven.apache.org/maven2 }
- { type: hosted, name: maven-custom }
- { type: group,  name: maven-public }
```

<!-- prettier-ignore-end -->

## 注意事项

#### Nexus 并不能识别 Ingress Controller 暴露的非 80 和 443 以外的端口
