# Elasticsearch

## 快速开始

#### 交互式设置 X-Pack 密码认证

```shell
bin/elasticsearch-setup-passwords interactive
```

## 收藏

-   [ES8 unable to authenticate user [kibana_system] for REST request#10076](https://github.com/bitnami/charts/issues/10076)

```yaml
kibana:
  elasticsearch:
    security:
      auth:
        enabled: true
        createSystemUser: true
        kibanaPassword: kibana_system
        elasticsearchPasswordSecret: elasticsearch
      tls:
        enabled: true
        existingSecret: elasticsearch-master-crt
        # coordinating-only
        # existingSecret: elasticsearch-coordinating-crt
        usePemCerts: true
  ingress:
    enabled: true
    hostname: kibana.example.org
```
