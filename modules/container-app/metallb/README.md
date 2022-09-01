## Helm 安装 MetalLB

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm upgrade metallb bitnami/metallb --install --namespace metallb --create-namespace --version 4.1.1
```

```bash
cat > IPAddressPool.yaml <<- "EOF"
apiVersion: metallb.io/v1beta1
kind: IPAddressPool
metadata:
  name: first-pool
  namespace: metallb
spec:
  addresses:
    - 192.192.192.6-192.192.192.6
EOF

kubectl apply -f IPAddressPool.yam
```
