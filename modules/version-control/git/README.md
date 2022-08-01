# Git

## 收藏

- [error: RPC failed； curl 56 OpenSSL SSL_read: Connection was reset, errno 10054](https://blog.csdn.net/dsqcsdn/article/details/104821042/)

- [Git 提交时的 emoji 表情使用指南](https://ibyte.blog.csdn.net/article/details/113336076)

- [.gitignore 忽略规则的匹配语法](https://blog.csdn.net/Mr_JavaScript/article/details/91788035)

## 常见问题

#### detected dubious ownership in repository at xxx To add an exception for this directory

```bash
git config --global --add safe.directory xxx
```

或者

```bash
export GIT_TEST_DEBUG_UNSAFE_DIRECTORIES=true
```

#### Windows 上 Git 保留可执行文件权限

> https://www.cnblogs.com/LoveBB/p/16076897.html

```bash
git update-index --chmod=+x a.sh
```

```bash
git ls-tree HEAD
```
