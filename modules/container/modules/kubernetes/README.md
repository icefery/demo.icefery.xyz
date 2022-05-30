# Kubernetes

## 常见问题

- 0/1 nodes are available: 1 node(s) had taint {node-role.kubernetes.io/master: }, that the pod didn't tolerate.

  ```bash
  kubectl taint nodes --all node-role.kubernetes.io/master-
  ```
