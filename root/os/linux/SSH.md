### 免密登录

> 将 `id_rsa.pub` 追加到 `authorized_keys` 中

```shell
ssh-keygen -t rsa -q -N "" -f ~/.ssh/id_rsa

ssh-copy-id node6.icefery.xyz
```

### 非交互式生成 SSH-KEY

```shell
ssh-keygen -t rsa -q -N "" -f ~/.ssh/id_rsa
```
