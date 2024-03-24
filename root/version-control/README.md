# 版本控制

## 常用命令

### 仅显示当前分支名称

```shell
git rev-parse --abbrev-ref HEAD

-- git 2.22 及更高版本
git branch --show-current
```

### 每次操作都需要认证

```shell
git config --global credential.helper store
```

### 使用 `git clone <URL> --depth=<NUMBER>` 后推送新远程仓库

```shell
git remote add old <OLD_URL>
git fetch --unshallow old
git push --set-upstream origin
```
