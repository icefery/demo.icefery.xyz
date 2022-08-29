# Nexus

## Helm 安装 Nexus

```bash
helm repo add sonatype https://sonatype.github.io/helm3-charts/

helm upgrade nexus sonatype/nexus-repository-manager --install --namespace nexus --create-namespace --values values.yaml --version 41.1.2
```