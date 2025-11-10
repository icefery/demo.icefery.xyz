## cert-manager

### 自签证书

```shell
kubectl get secret/my-cert-01 -n dev -o jsonpath='{.data.ca\.crt}'  | base64 --decode > ca.crt
kubectl get secret/my-cert-01 -n dev -o jsonpath='{.data.tls\.crt}' | base64 --decode > tls.crt
kubectl get secret/my-cert-01 -n dev -o jsonpath='{.data.tls\.key}' | base64 --decode > tls.key
```
