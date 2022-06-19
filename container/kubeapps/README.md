## Kubeapps

## Install

### Helm

```bash
helm repo add bitnami https://charts.bitnami.com/bitnami

helm upgrade kubeapps bitnami/kubeapps \
    --install \
    --create-namespace \
    --namespace kubeapps \
    --version 8.1.11 \
    --values values.yaml
```

## Access

```bash
kubectl --namespace kubeapps create serviceaccount kubeapps-operator

kubectl create clusterrolebinding kubeapps-operator --clusterrole=cluster-admin --serviceaccount=kubeapps:kubeapps-operator

cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Secret
metadata:
  name: kubeapps-operator-token
  namespace: kubeapps
  annotations:
    kubernetes.io/service-account.name: kubeapps-operator
type: kubernetes.io/service-account-token
EOF
```

```bash
kubectl --namespace kubeapps get secret kubeapps-operator-token -o jsonpath='{.data.token}' -o go-template='{{.data.token | base64decode}}' && echo
```
