# 版本控制

## 常用命令

### 仅显示当前分支名称

```shell
git rev-parse --abbrev-ref HEAD

-- git 2.22 及更高版本
git branch --show-current
```

### 每次操作都需要认证

```bash
git config --global credential.helper store
```
