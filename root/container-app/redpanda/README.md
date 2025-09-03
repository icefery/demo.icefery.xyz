# Redpanda

## 认证

```shell
rpk cluster config set superusers ['admin']

rpk security user create 'admin' -p 'admin' --mechanism='SCRAM-SHA-512' -X admin.hosts='0.0.0.0:9644'

rpk cluster config set enable_sasl true
```
